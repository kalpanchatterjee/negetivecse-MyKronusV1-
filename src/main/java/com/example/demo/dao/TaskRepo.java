package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TaskModel;
import com.example.demo.util.Utilcollection;

@Repository
public class TaskRepo {


	@Autowired
	JdbcTemplate jdbctm;
	@Value("${com.rs.lighttpd.path}")
	private String lighttpd;



	public List<TaskModel> getTaskDetails(TaskModel tm) {


		String userId="";
		String companyId="";
		String presentDate = (tm.getPresentDate()!=null && !tm.getPresentDate().equals("")) ? tm.getPresentDate() :"";
		String filterVal = (tm.getFilterVal()!=null && !tm.getFilterVal().equals("")) ? tm.getFilterVal() :"";
		String text = (tm.getText()!=null && tm.getText()!=null && !tm.getText().trim().equals("")) ? tm.getText().trim() :"";
		String typeofDate=tm.getTypeofDate()!=null && !tm.getTypeofDate().equals("")?tm.getTypeofDate():"NofilterDate";
		String sortValue=tm.getSortValue();
		String viewForTask=tm.getViewForTask()!=null && !tm.getViewForTask().equals("")?tm.getViewForTask():"";
		userId=tm.getUser_id();
		companyId=tm.getCompany_id();
		String localoffsetTimeZone = (tm.getLocaloffsetTimeZone()!=null && !tm.getLocaloffsetTimeZone().equals("")) ? tm.getLocaloffsetTimeZone() :"";
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);

		String taskId=(tm.getTask_id()!=null && !tm.getTask_id().equals("")) ? tm.getTask_id() :"";
		String serverTimeZone = Utilcollection.getServerTimeZone();

		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT cmt.task_status, cmt.task_id, cmt.task_name, ifnull(cmt.priority_id,'') as priority_id,CONCAT(cu.user_first_name,' ',user_last_name) AS uname ,");

		sql.append("  IFNULL( DATE_FORMAT(CONVERT_TZ(CONCAT(cmt.task_start_date ,' ', cmt.task_start_time), '"+serverTimeZone+"',  ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%h:%i %p'),'')AS 'startTime',");
		sql.append("  IFNULL( DATE_FORMAT(CONVERT_TZ(CONCAT(cmt.task_end_date ,' ', cmt.task_end_time),'"+serverTimeZone+"',  ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%h:%i %p'),'')AS 'endTime',");
		sql.append("  IFNULL( DATE_FORMAT(CONVERT_TZ(CONCAT(cmt.task_start_date ,' ', cmt.task_start_time), '"+serverTimeZone+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%b %d %Y'),'') AS 'startDate',");
		sql.append("  IFNULL( DATE_FORMAT(CONVERT_TZ(CONCAT(cmt.task_end_date ,' ', cmt.task_end_time),'"+serverTimeZone+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%b %d %Y'),'') AS 'endDate',");
		sql.append("  IFNULL( DATE_FORMAT(CONVERT_TZ(cmt.task_due_by_date, '"+serverTimeZone+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%b %d %Y'),'-') AS 'taskDueBy',");
		sql.append(" DATE_FORMAT(IFNULL((IFNULL(cu.last_updated_timestamp, cu.created_timestamp)), CURRENT_TIMESTAMP),'%y%m%d%H%i%s%f') AS imgTimeStamp,");
		sql.append("  IFNULL( cmt.task_due_by_date, '-') AS 'taskDueDate',");
		sql.append("  IFNULL(cmt.actual_time,'') AS actTime,ifnull(cmt.task_comment,'') as taskComment,IFNULL(cmt.elapsed_time,'') AS elapsed_time, cmt.created_by, cmt.task_billable, cmt.actual_time_modified, cmt.task_type, ");
		sql.append("  IFNULL(det.totAct, '') as detActTime,cu.user_timezone,IFNULL(cu.user_timezone_intz,'IST') AS bbm_pin, ");
		sql.append("  IFNULL(cmt.task_group,'') as group_id, IFNULL(cmg.group_code, '') as group_code, IFNULL(cmg.group_name, '') as group_name ");
		sql.append(" FROM co_mykronus_tasks cmt ");
		sql.append(" JOIN co_mykronus_tasks_details cmtd on cmtd.task_id=cmt.task_id ");
		sql.append(" LEFT JOIN co_users cu ON cu.user_id=cmt.created_by ");
		sql.append(" LEFT JOIN co_mykronus_groups cmg ON cmg.group_id=cmt.task_group ");
		sql.append(" LEFT JOIN (SELECT SEC_TO_TIME(SUM(IFNULL(TIME_TO_SEC(actual_time),0) ))  as totAct, task_id ");
		sql.append("  					FROM co_mykronus_tasks_details WHERE status='Y' group by task_id ) as det on  det.task_id =  cmt.task_id ");

		sql.append(" WHERE cmt.company_id="+companyId);
		sql.append(" AND cmt.status='Y'");
		if(!viewForTask.equals("team")) {
			sql.append(" AND cmt.created_by="+userId);
		}else {
			sql.append(" AND cmt.task_type='B' ");
		}
		if(!taskId.equals("")) {
			sql.append(" AND cmt.task_id="+taskId);
		}else{
			if(!filterVal.equals("")) {
				sql.append(" AND cmt.task_status='"+filterVal+"'");
			}
			if(!text.equals("")){
				sql.append(" AND( (LOWER(cmt.task_name) LIKE ('%"+text+"%') ) ");
				if(!viewForTask.equals("team")) {
					sql.append(" AND cmt.created_by="+userId);
				}

				sql.append(" OR (LOWER(CONCAT(cu.user_first_name,' ',cu.user_last_name)) LIKE ('%"+text+"%') ) ");
				if(!viewForTask.equals("team")) {
					sql.append(" AND cmt.created_by="+userId);

				}
				sql.append(")");


			}
			if(typeofDate.equals("NofilterDate")) {
				sql.append(" AND (cmt.task_status NOT IN('Completed','Cancel') ");
				sql.append(" 		 OR (cmtd.task_status='Completed' ");
				sql.append(" 				AND (DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp,'"+serverTimeZone+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%Y-%m-%d')='"+presentDate+"'))");
				sql.append(" 		)");
			}
			if(typeofDate.equals("filterDate")) {
				sql.append(" AND ( ");
				sql.append("   (cmtd.task_status NOT IN('Completed','Cancel') AND (DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp,'"+serverTimeZone+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%Y-%m-%d')='"+presentDate+"')) ");
				sql.append(" 	   OR ");
				sql.append("   (cmtd.task_status='Completed' AND (DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp,'"+serverTimeZone+"',ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%Y-%m-%d')='"+presentDate+"')) ");
				sql.append(" ) ");
			}
		}
		sql.append(" GROUP BY cmtd.task_id  ");	


		if(sortValue!=null && sortValue.equals("name")){
			sql.append(" ORDER BY cmt.task_name ASC  ");	
		}else if(sortValue!=null && sortValue.equals("status")){
			sql.append(" ORDER BY IF(cmt.task_status='Completed',4,IF(cmt.task_status='Paused',3,IF(cmt.task_status='Created',1,2))) ASC");
		}else if(sortValue!=null && sortValue.equals("recent")){
			sql.append(" ORDER BY cmt.created_timestamp DESC  ");	
		}
		else if(sortValue!=null && sortValue.equals("high")){
			sql.append("  ORDER BY IF(cmt.priority_id=1,1,IF(cmt.priority_id=2,2,IF(cmt.priority_id=3,3,4))) ASC ");	
		}
		else if(sortValue!=null && sortValue.equals("low")){
			sql.append("  ORDER BY IF(cmt.priority_id=1,1,IF(cmt.priority_id=2,2,IF(cmt.priority_id=3,3,4))) DESC ");	
		}
		else {
			sql.append("  ORDER BY");
			if(viewForTask.equals("team")) {
				sql.append("  CONCAT(cu.user_first_name,' ',cu.user_last_name) ASC ,");	
			}

			sql.append("  IFNULL(group_name,'Z') ASC, cmt.created_timestamp DESC ");

		}

		List<TaskModel> getTaskDetails=jdbctm.query(sql.toString(), BeanPropertyRowMapper.newInstance(TaskModel.class));


		for(TaskModel vtm:getTaskDetails) {

			if(!vtm.getTask_status().equals("Completed")) {
				sql = new StringBuilder("");
				sql.append(" SELECT IFNULL(TIMEDIFF( ");
				sql.append("	date_format(convert_tz('"+Utilcollection.getTimeStamp()+"','"+serverTimeZone+"','"+(vtm.getUser_timezone()==null ?  localoffsetTimeZone :  vtm.getUser_timezone())+"'), '%Y-%m-%d %H:%i:%s') ");
				sql.append("   , ");
				sql.append("  (SELECT date_format(convert_tz(MIN(created_timestamp), '"+serverTimeZone+"', '"+(vtm.getUser_timezone()==null ?  localoffsetTimeZone : vtm.getUser_timezone())+"'), '%Y-%m-%d %H:%i:%s') from co_mykronus_tasks_details where task_id="+vtm.getTask_id()+" AND task_status='Inprogress' ) ");
				sql.append(" ),'') as elapsed_time ");
				List<TaskModel> getelapsed_time=jdbctm.query(sql.toString(), BeanPropertyRowMapper.newInstance(TaskModel.class));
				for(TaskModel v:getelapsed_time) {
					vtm.setElapsed_time(v.getElapsed_time().substring(0,v.getElapsed_time().lastIndexOf(".")>0?v.getElapsed_time().lastIndexOf("."):v.getElapsed_time().length()));

				}


			}
			vtm.setActTimeDetail(vtm.getDetActTime());
			vtm.setImageUrl(lighttpd+"/userimages/"+vtm.getCreated_by()+".jpeg");


		}
		return getTaskDetails;

	}

}
