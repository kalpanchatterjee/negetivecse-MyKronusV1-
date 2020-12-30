package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.userModel;
import com.example.demo.util.Utilcollection;



@Repository
public class userStatusRepo {
	
	@Autowired
	JdbcTemplate jdbctm;
	@Value("${com.rs.lighttpd.path}")
	private String lighttpd;
	
	
	public List<userModel> getUserStatusDetails(userModel udm){
		
		String localoffsetTimeZone = (udm.getLocaloffsetTimeZone()!=null && !udm.getLocaloffsetTimeZone().equals("")) ? udm.getLocaloffsetTimeZone() :"";
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		StringBuilder SQL=new StringBuilder();
		SQL.append(" SELECT * FROM ( ");
		SQL.append(" 		SELECT 'workdata' AS dataType, cuud.user_id, cuud.company_id,  cuud.usage_detail_id AS id,");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%d-%b-%Y') AS userUsageDate, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%h:%i %p') AS userUsageTime, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%d-%b-%Y %r') AS userUsageDateTimestamp, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%T') AS userUsageTime2,");
		SQL.append(" 		cuud.STATUS, cuud.lat_position, cuud.long_position, cuud.user_usage_date_timestamp AS createdTimestamp,ifnull(cuud.status_comment,'') as statusComment, ");

		//---- below columns are used to fetch work and break hours
		SQL.append("		 ifnull(time_format(cuud.total_work_hours,'%H:%i'),'-') as WorkHrs, ");
		SQL.append("		 ifnull(time_format(cuud.total_break_hours,'%H:%i'),'-') as BreakHrs, ");
		SQL.append("		 ifnull(TIME_FORMAT(ADDTIME(SEC_TO_TIME(IFNULL(TIME_TO_SEC(cuud.total_work_hours),0)), SEC_TO_TIME(IFNULL(TIME_TO_SEC(cuud.total_break_hours),0))) , '%H:%i'), '-' ) AS TotalHrs,cu.user_timezone,IFNULL(cu.user_timezone_intz,'IST') AS bbm_pin,");
		SQL.append("		 TIMEDIFF(DATE_FORMAT(CONVERT_TZ(CURRENT_TIMESTAMP, '-7:00', user_timezone), '%Y-%m-%d %H:%i:%s'), ");
		SQL.append("		              DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp, '-7:00', user_timezone), '%Y-%m-%d %H:%i:%s')) as currElapsedTime ");
		SQL.append(" 		FROM co_user_usage_details cuud ");
		SQL.append(" 		JOIN co_users cu ON cu.user_id = cuud.user_id ");
		SQL.append(" 		where cuud.user_id="+udm.getUser_id()+" and cuud.company_id="+udm.getCompany_id()+" ");
		if(!udm.getPresentdate().equals("")){
			SQL.append(" 	AND DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%Y-%m-%d') ='").append(udm.getPresentdate()).append("' " ); //DATE_FORMAT(CONVERT_TZ(created_timestamp, '+05:30', '+05:30'), '%Y-%m-%d')
		}
		SQL.append("   UNION ");
		SQL.append(" 		SELECT 'optiondata' AS dataType, cuwc.user_id, cuwc.company_id, cuwc.user_workday_cal_id AS id,");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuwc.created_timestamp,'"+Utilcollection. getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%d-%b-%Y') AS userUsageDate, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuwc.created_timestamp,'"+Utilcollection. getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%h:%i %p')  AS userUsageTime, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuwc.created_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%d-%b-%Y %r') AS userUsageDateTimestamp, ");
		SQL.append(" 		DATE_FORMAT(CONVERT_TZ(cuwc.created_timestamp,'"+ Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')  ),'%T') AS userUsageTime2,");
		SQL.append(" 		cuwc.attendance_type AS STATUS,  '' AS lat_position,  '' AS long_position, cuwc.created_timestamp AS createdTimestamp, ifnull(cuwc.attendance_comment,'') as statusComment, ");

		SQL.append("		 '-'  AS WorkHrs, ");
		SQL.append("		 '-'  AS BreakHrs, ");
		SQL.append("		 '-'  AS TotalHrs,cu.user_timezone,IFNULL(cu.user_timezone_intz,'IST') AS bbm_pin ,");
		SQL.append("		 '-' as currElapsedTime ");
		SQL.append(" 		FROM co_users_workday_calendar cuwc ");
		SQL.append(" 		JOIN co_users cu ON cu.user_id = cuwc.user_id ");
		SQL.append(" 		where cuwc.user_id="+udm.getUser_id()+" and cuwc.company_id="+udm.getCompany_id()+" AND cuwc.status='Y' ");
		if(!udm.getPresentdate().equals("")){
			SQL.append(" 	AND cuwc.attendance_date ='").append(udm.getPresentdate()).append("' " ); //DATE_FORMAT(CONVERT_TZ(created_timestamp, '+05:30', '+05:30'), '%Y-%m-%d')
		}
		SQL.append("  ) AS wData ");
		SQL.append(" ORDER BY createdTimestamp ASC ");

		System.out.println(SQL.toString());
		System.out.println("commiting Yes!!!!!!!!");

		List<userModel> userStatusList=jdbctm.query(SQL.toString(), BeanPropertyRowMapper.newInstance(userModel.class));
		System.out.println(lighttpd);
		return userStatusList;
	}
	
	

	public String updateUserStatus(userModel udm){
		String result="";
		if(udm.getSTATUS().equals("pause")){
			udm.setSTATUS("break_start");
		}
		else if(udm.getSTATUS().equals("resume")){
			udm.setSTATUS("break_close");
		}

		String localoffsetTimeZone = (udm.getLocaloffsetTimeZone()!=null && !udm.getLocaloffsetTimeZone().equals("")) ? udm.getLocaloffsetTimeZone() :"";
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);

		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		String query =  "SELECT ifnull(cuud.STATUS,'') AS prevStatus FROM co_user_usage_details cuud JOIN co_users cu ON cuud.user_id=cu.user_id WHERE cuud.user_id ="+udm.getUser_id()+" AND cuud.company_id="+udm.getCompany_id()+" AND DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+Utilcollection.getServerTimeZone()+"',ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')),'%Y-%m-%d')='"+(udm.getPresentdate().equals("") ? Utilcollection.getDate() : udm.getPresentdate())+"' ORDER BY cuud.usage_detail_id DESC LIMIT 1" ;
		String prevStatus="";
		try {
			prevStatus = jdbctm.queryForObject(query.toString(), String.class);
		} catch (Exception e) {
			prevStatus= "";
		}
		System.out.println("--->"+prevStatus);
		String prevHrs="";
		boolean insertFlag=true;
		if( (prevStatus.equals("") && udm.getSTATUS().equals("start")) 
				|| (prevStatus.equals("stop") && udm.getSTATUS().equals("start")) 
				|| (prevStatus.equals("start") && udm.getSTATUS().equals("break_start"))
				|| (prevStatus.equals("break_close") && udm.getSTATUS().equals("break_start"))
				|| (prevStatus.equals("break_start") && udm.getSTATUS().equals("break_close"))
				|| (!prevStatus.equals("") && !prevStatus.equals("stop") && udm.getSTATUS().equals("stop")) 
				){ 
			insertFlag=true;
		}else{
			insertFlag=false; 
		}
		if(insertFlag){
			Time cuurTime = Utilcollection.getTime();
			String usageTime =String.valueOf(cuurTime);
			String totHrs="";
			if( !prevStatus.equals("") && !prevStatus.equals("stop")){
				query =  "SELECT ifnull(cuud.user_usage_time,'') AS prevHrs FROM co_user_usage_details cuud JOIN co_users cu on cuud.user_id=cu.user_id WHERE cuud.user_id ="+udm.getUser_id()+" AND cuud.company_id="+udm.getCompany_id()+" AND DATE_FORMAT(CONVERT_TZ(cuud.user_usage_date_timestamp,'"+Utilcollection.getServerTimeZone()+"',ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')),'%Y-%m-%d')='"+(udm.getPresentdate().equals("") ? Utilcollection.getDate() : udm.getPresentdate())+"' ORDER BY cuud.usage_detail_id DESC LIMIT 1" ;
				try {
					prevHrs=jdbctm.queryForObject(query.toString(), String.class);
				} catch (Exception e) {
					prevHrs= "";
				}
				System.out.println("prev Hours::::"+prevHrs);
				if(!prevHrs.equals("")){
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
					java.util.Date t1 = null;
					java.util.Date t2 = null;
					try {
						t1 = format.parse(prevHrs);
						t2 = format.parse(usageTime);
						long diff =0;
						//in milliseconds
						if(t2.getTime() < t1.getTime()){//--- this in case of prevHrs(Previous Status time) belongs to previous day and usageTime(Current Status time) belongs to new day
							String totTime = "23:59:59";
							java.util.Date totFormtd = format.parse(totTime);
							long prevDayDiff = totFormtd.getTime() -  t1.getTime();
							String newTime = "00:00:00";
							java.util.Date newFormtd = format.parse(newTime);
							long newDayDiff =  t2.getTime() - newFormtd.getTime() ;
							diff = prevDayDiff + newDayDiff;

						}else{
							diff = t2.getTime() - t1.getTime();
						}

						long diffSeconds = diff / 1000 % 60;
						long diffMinutes = diff / (60 * 1000) % 60;
						long diffHours = diff / (60 * 60 * 1000) % 24;
						String hh = diffHours < 10 ? "0"+String.valueOf(diffHours) : String.valueOf(diffHours);
						String mm = diffMinutes < 10 ? "0"+String.valueOf(diffMinutes) : String.valueOf(diffMinutes);
						String ss = diffSeconds < 10 ? "0"+String.valueOf(diffSeconds) : String.valueOf(diffSeconds);
						totHrs = hh+":"+mm+":"+ss;
						//System.out.println("totHrs:::"+totHrs);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			StringBuilder sql=new StringBuilder();
			sql.append("INSERT INTO co_user_usage_details (user_id,company_id,user_usage_date,user_usage_time,user_usage_date_timestamp,STATUS,long_position,lat_position,device_type,status_comment,address1,address2,address3,city,zip_state,country)");
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int ctr=0;
			ctr = jdbctm.update(
					connection -> {
						PreparedStatement ps = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, String.valueOf(udm.getUser_id()));
						ps.setString(2,  String.valueOf(udm.getCompany_id()));
						ps.setDate(3, Utilcollection.getDate());
						ps.setTime(4, cuurTime);
						ps.setTimestamp(5,Utilcollection. getTimeStamp());
						ps.setString(6, udm.getSTATUS());
						ps.setString(7, udm.getLong_position());
						ps.setString(8, udm.getLong_position());
						ps.setString(9, "test");
						ps.setString(10, udm.getStatusComment());
						ps.setString(11,udm.getAddress1());
						ps.setString(12, udm.getAddress2());
						ps.setString(13, udm.getAddress3());
						ps.setString(14, udm.getCity());
						ps.setString(15, udm.getZip_code());
						ps.setString(16, udm.getCountry());
						return ps;
					}, keyHolder);

			if(ctr != 0){
				int gId =0;
				Number key = keyHolder.getKey();
				gId = key.intValue();
				boolean Update=true;
				if(gId !=0 && !totHrs.equals("")){

					if( (prevStatus.equals("start") && udm.getSTATUS().equals("stop")) 
							||  (prevStatus.equals("start") &&  udm.getSTATUS().equals("break_start")) 
							||  (prevStatus.equals("break_close") &&  udm.getSTATUS().equals("break_start")) 
							||  (prevStatus.equals("break_close") &&  udm.getSTATUS().equals("stop")) ){
						String sql1="UPDATE co_user_usage_details SET total_work_hours='"+totHrs+"' WHERE usage_detail_id= "+gId;
						try {
							jdbctm.update(sql1);
						} catch (Exception e2) {
							// TODO: handle exception
							Update=false;
						}


					}
					else if( (prevStatus.equals("break_start") && udm.getSTATUS().equals("break_close")) 
							||  (prevStatus.equals("break_start") && udm.getSTATUS().equals("stop")) ){
						String sql1="UPDATE co_user_usage_details SET total_break_hours='"+totHrs+"' WHERE usage_detail_id= "+gId;
						try {
							jdbctm.update(sql1);
						} catch (Exception e2) {
							// TODO: handle exception
							Update=false;
						}
					}
				}
				if(Update) {
					result="success";
				}

			}
		}else {
			result="refresh";
		}
		return result;
	}

}
