package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
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

	public List<TaskModel> createTask(TaskModel tm) {
		String taskName=tm.getTask_name();
		String groupId = (tm.getGroup_id()!=null && !tm.getGroup_id().equals("")) ? tm.getGroup_id() :"";
		String dueDate = (tm.getTaskDueDate()!=null && !tm.getTaskDueDate().equals("")) ? tm.getTaskDueDate() :"";
		String userId=tm.getUser_id();
		String companyId=tm.getCompany_id();
		String localoffsetTimeZone = (tm.getLocaloffsetTimeZone()!=null && !tm.getLocaloffsetTimeZone().equals("")) ? tm.getLocaloffsetTimeZone() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		StringBuilder sql=new StringBuilder();
		try {
			sql.append("INSERT INTO co_mykronus_tasks (task_name,task_status,company_id,created_by,created_date,created_timestamp,task_due_by_date, task_group)");
			sql.append("VALUES(?,?,?,?,?,?,?,?)");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int ctr=0;
			ctr = jdbctm.update(
					connection -> {
						PreparedStatement ps = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
						ps.setString(1,taskName );
						ps.setString(2,  "Created" );
						ps.setString(3, companyId);
						ps.setString(4,userId);
						ps.setDate(5, Utilcollection.getDate());
						ps.setTimestamp(6, Utilcollection.getTimeStamp());
						if(dueDate.equals("")) {
							ps.setDate(7, Utilcollection.getDate());
						}else {
							ps.setString  (7, dueDate);
						}
						
						ps.setString(8, groupId);
						
						return ps;
					}, keyHolder);
				if(ctr != 0){
					
					Number key = keyHolder.getKey();
//					System.out.println("----> "+key.intValue());
					StringBuilder sql1=new StringBuilder();
					sql1.append("INSERT INTO co_mykronus_tasks_details (task_id,task_status,company_id,created_by,created_date,last_updated_date,last_updated_timestamp,last_updated_by,created_timestamp) ");
					sql1.append("VALUES(?,?,?,?,?,?,?,?,?)");
					ctr = jdbctm.update(
							connection -> {
								PreparedStatement ps = connection.prepareStatement(sql1.toString(),Statement.RETURN_GENERATED_KEYS);
								ps.setLong(1, key.intValue());
								ps.setString(2, "Created");
								ps.setString(3, companyId);
								ps.setString(4,userId);
								ps.setDate(5, Utilcollection.getDate());
								ps.setDate(6, Utilcollection.getDate());
								ps.setTimestamp(7, Utilcollection.getTimeStamp());
								ps.setString(8, userId);
								ps.setTimestamp(9, Utilcollection.getTimeStamp());
								
								
								return ps;
							}, keyHolder);
							
					tm.setTask_id( String.valueOf(key.intValue()));
					
				}
			
		}catch (Exception e) {
			
		}
		List<TaskModel>createdTaskDetails=getTaskDetails(tm);
		return createdTaskDetails;
	}
		


	public List<TaskModel> updateTask(TaskModel tm) {
		String taskName=tm.getTask_name();
		String userId=tm.getUser_id();
		String companyId=tm.getCompany_id();
		String act_time="00:00";
		List<TaskModel>updatedTaskDetails=null;
		StringBuilder sql=new StringBuilder();
		String status = (tm.getTask_status()!=null && !tm.getTask_status().equals("")) ? tm.getTask_status() :"";
		System.out.println("status-->"+status);
		try {
			if(status.equals("Created")) {

				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_start_date='"+Utilcollection.getDate()+"'");
				sql.append(" , task_start_time='"+Utilcollection.getTime()+"'");
				sql.append(" ,task_status='Inprogress'");

			}else if(status.equals("Inprogress")) {
				
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_status='Inprogress' ");
			}else if(status.equals("Paused")) {
				
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_end_date='"+Utilcollection.getDate()+"'");
				sql.append(" , task_end_time='"+Utilcollection.getTime()+"'");
				sql.append(" ,task_status='Paused'");
				
			}else if(status.equals("Completed")) {
				
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_status='Completed'");
				sql.append(" , task_end_date='"+Utilcollection.getDate()+"'");
				sql.append(" , task_end_time='"+Utilcollection.getTime()+"'");
			}
			else if(status.equals("Cancel")) {
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET status='N'");
			
			}
			else if(status.equals("Reopen")) {
				
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_status='Inprogress' ");
				sql.append(", elapsed_time=null");
			
			}else {
				sql.append(" UPDATE co_mykronus_tasks  ");
				sql.append(" SET task_name='"+taskName+"'");
			}
			sql.append(" , last_updated_date= '"+Utilcollection.getDate()+"'");
			sql.append(" ,last_updated_timestamp=  '"+Utilcollection.getTimeStamp()+"'");
			sql.append(" , last_updated_by="+userId);
			sql.append("  WHERE task_id="+tm.getTask_id());
			
			jdbctm.update(sql.toString());
			String elapsed_time="";
			if(status.equals("Paused")) {
				 String query="SELECT MAX(created_timestamp) AS Tasktimestamp FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id()  ; 
				 String taskTime=jdbctm.queryForObject(query, String.class);
				 
				 query=" SELECT CONCAT('', TIMEDIFF( date_format('"+Utilcollection.getTimeStamp()+"', '%Y-%m-%d %H:%i:%s'), date_format('"+taskTime+"', '%Y-%m-%d %H:%i:%s') )) as act_time " ; 
				 act_time=jdbctm.queryForObject(query, String.class);	
				 
				 query="SELECT task_detail_id FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id()+" ORDER BY task_detail_id DESC LIMIT 1 "; 
				 String latestDetailId=jdbctm.queryForObject(query, String.class);	
				 
				 String sqlInsert1="   UPDATE co_mykronus_tasks_details SET actual_time= '"+act_time+"', task_status_stop_timestamp=  '"+Utilcollection.getTimeStamp()+"', last_updated_by="+userId+"  WHERE task_detail_id="+latestDetailId;
				 jdbctm.update(sqlInsert1);
			}
			if(status.equals("Completed")) {
				String query="SELECT task_status FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id()+" ORDER BY task_detail_id DESC LIMIT 1 "; 
				String status_prev=jdbctm.queryForObject(query, String.class);
				if(!status_prev.equals("Paused") && !status_prev.equals("Created") ) {
					 query="SELECT MAX(created_timestamp) AS Tasktimestamp FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id();
					 String max_timestamp=jdbctm.queryForObject(query, String.class);
					 
					 query=" SELECT CONCAT('',TIMEDIFF( date_format('"+Utilcollection.getTimeStamp()+"', '%Y-%m-%d %H:%i:%s') , date_format('"+max_timestamp+"', '%Y-%m-%d %H:%i:%s') )) as act_time " ; 
					 act_time=jdbctm.queryForObject(query, String.class);
					 
					 query="SELECT task_detail_id FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id()+" ORDER BY task_detail_id DESC LIMIT 1 "; 
					 String latestDetailId =jdbctm.queryForObject(query, String.class);
					 
					 String sqlInsert1="   UPDATE co_mykronus_tasks_details SET actual_time= '"+act_time+"' , task_status_stop_timestamp=  '"+Utilcollection.getTimeStamp()+"', last_updated_by="+userId+"    WHERE task_detail_id="+latestDetailId;
					 jdbctm.update(sqlInsert1);
			 	}else {
			 		query="SELECT task_detail_id FROM co_mykronus_tasks_details  WHERE task_id="+tm.getTask_id()+" ORDER BY task_detail_id DESC LIMIT 1 "; 
			 		String latestDetailId =jdbctm.queryForObject(query, String.class);
			 		
			 		String sqlInsert1="   UPDATE co_mykronus_tasks_details SET  task_status_stop_timestamp=  '"+Utilcollection.getTimeStamp()+"', last_updated_by="+userId+"    WHERE task_detail_id="+latestDetailId;
			 		jdbctm.update(sqlInsert1);
			 	}
			}
			
			if(status.equals("Reopen") || status.equals("Created")) {
				status="Inprogress";
			}
			StringBuilder sql1=new StringBuilder();
			if(!status.equals("")) {
				sql1.append(" INSERT INTO co_mykronus_tasks_details");
				sql1.append(" (task_id,task_status,company_id,created_by,created_date,created_timestamp )");
				sql1.append("  VALUES(?,?,?,?,?,? )");
				int ctr=0;
				ctr = jdbctm.update(sql1.toString(),tm.getTask_id(),status,companyId,userId,Utilcollection.getDate(),Utilcollection.getTimeStamp());
				if(status.equals("Completed")) {
					sql1=new StringBuilder();
					sql1.append(" SELECT CONCAT('', TIMEDIFF( ");
					sql1.append(" 	 (SELECT MAX(created_timestamp) from co_mykronus_tasks_details where task_id="+tm.getTask_id()+" AND task_status='Completed' ) ");
					sql1.append("   , ");
					sql1.append("  (SELECT MIN(created_timestamp) from co_mykronus_tasks_details where task_id="+tm.getTask_id()+" AND task_status='Inprogress' ) ");
					sql1.append(" ) ) ");
					elapsed_time=jdbctm.queryForObject(sql1.toString(), String.class);
					System.out.println(sql1.toString());
					String sqlInsert1="   UPDATE co_mykronus_tasks SET elapsed_time= '"+elapsed_time+"' WHERE task_id="+tm.getTask_id();
					jdbctm.update(sqlInsert1);
				}
			
			}
			
			 updatedTaskDetails=getTaskDetails(tm);	
		} catch (Exception e) {
		}
		return updatedTaskDetails;

	}

	public List<TaskModel> taskDetails(TaskModel tm) {
		String taskId = (tm.getTask_id()!=null && !tm.getTask_id().equals("")) ? tm.getTask_id():"";
		String localoffsetTimeZone = (tm.getLocaloffsetTimeZone()!=null && !tm.getLocaloffsetTimeZone().equals("")) ? tm.getLocaloffsetTimeZone() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT cmtd.task_detail_id, cmtd.task_id, cmtd.task_status, IFNULL(cmtd.actual_time,'-') AS actual_time, cmtd.actual_time_modified, ");
		//sql.append(" IFNULL(DATE_FORMAT(CONVERT_TZ(task_end_time,'"+UtilCollections.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%h:%i %p'),'')AS 'endTime',");
		sql.append(" 	IFNULL ( DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp, '"+Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%b %d %Y'),'') AS 'startDate',");
		sql.append("  	IFNULL ( DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp,'"+Utilcollection.getServerTimeZone()+"',  ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%h:%i %p'),'') AS 'startTime',");
		sql.append("  	IFNULL ( DATE_FORMAT(CONVERT_TZ(cmtd.created_timestamp,'"+Utilcollection.getServerTimeZone()+"',  ifnull(cu.user_timezone, '"+localoffsetTimeZone+"') ),'%H:%i:%s'),'') AS 'startTime',");
		sql.append("   IFNULL (cmtd.elapsed_time,'') AS elapsed_time,cmtd.created_by");
		sql.append("  FROM co_mykronus_tasks_details cmtd ");
		sql.append("  JOIN co_users cu ON cu.user_id = cmtd.created_by	");
		sql.append("  WHERE cmtd.task_id="+taskId);
		sql.append("    AND cmtd.task_status NOT IN('Created')");
		//sql.append("  ORDER BY cmtd.created_timestamp DESC ");
		sql.append("  ORDER BY cmtd.task_detail_id DESC ");
		List<TaskModel> taskDetails= jdbctm.query(sql.toString(),BeanPropertyRowMapper.newInstance(TaskModel.class));
		return taskDetails;
	}

	public List<TaskModel> updatePriority(TaskModel tm) {
		
		String taskId = tm.getTask_id();
		String priority_id = tm.getPriority_id();
		String sessUserId =tm.getUser_id();
		List<TaskModel> updatePriorityTaskDetails =null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append(" UPDATE co_mykronus_tasks  ");
			sql.append(" SET priority_id='"+priority_id+"'");
			sql.append(" , last_updated_date= '"+ Utilcollection.getDate()+"'");
			sql.append(" , last_updated_timestamp=  '"+Utilcollection.getTimeStamp()+"'");
			sql.append(" , last_updated_by="+sessUserId);
			sql.append("  WHERE task_id="+taskId);
			jdbctm.update(sql.toString());
			updatePriorityTaskDetails= getTaskDetails(tm);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return updatePriorityTaskDetails;
	}

}
