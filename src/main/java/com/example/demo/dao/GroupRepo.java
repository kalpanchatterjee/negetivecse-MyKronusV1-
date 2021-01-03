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

import com.example.demo.model.GroupModel;
import com.example.demo.util.Utilcollection;

@Repository
public class GroupRepo {
	
	@Autowired
	JdbcTemplate jdbctm;
	@Value("${com.rs.lighttpd.path}")
	private String lighttpd;

	public List<GroupModel> listGroup(GroupModel gm) {
		
//		int userId=gm.getUser_id();
		int companyId=gm.getCompany_id();
		int group_id = gm.getGroup_id() ;
		String localoffsetTimeZone = gm.getLocalOffsetTime() !=null ? gm.getLocalOffsetTime() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT CONCAT(cu.user_first_name,' ',cu.user_last_name)AS created_by, cmg.group_id, cmg.group_name, IFNULL(cmg.group_desc,'') AS group_desc, cmg.company_id, cmg.group_code,");
		sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(cmg.created_timestamp, '"+Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')), '%b %d %Y'),'') AS 'created_date',");
		sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(cmg.created_timestamp, '"+Utilcollection.getServerTimeZone()+"', ifnull(cu.user_timezone, '"+localoffsetTimeZone+"')), '%h:%i %p'),'') AS 'created_time'");
		sql.append(" FROM co_mykronus_groups cmg");
		sql.append(" JOIN co_users cu");
		sql.append(" ON cu.user_id=cmg.created_by");
		sql.append(" WHERE cmg.company_id="+companyId);
		if(group_id!=0) {
			 sql.append(" AND cmg.group_id="+group_id); 
		 }
		sql.append(" AND cmg.status='Y' ");
		sql.append(" ORDER BY cmg.created_timestamp DESC ");
		System.out.println(sql.toString());
		
		List<GroupModel> grouplist = jdbctm.query(sql.toString(), BeanPropertyRowMapper.newInstance(GroupModel.class));
		
		return grouplist;
	}

	public List<GroupModel> createGroup(GroupModel gm) {
		int userId = gm.getUser_id();
		int companyId=gm.getCompany_id();
		System.out.println("----------abc");
		String localoffsetTimeZone = gm.getLocalOffsetTime() !=null ? gm.getLocalOffsetTime() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		
		String group_code=(gm.getGroup_code()!=null && !gm.getGroup_code().equals("")) ? gm.getGroup_code() :"";
		String group_name=(gm.getGroup_name()!=null && !gm.getGroup_name().equals("")) ? gm.getGroup_name() :"";
		String group_desc=(gm.getGroup_desc()!=null && !gm.getGroup_desc().equals("")) ? gm.getGroup_desc() :"";
		
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" INSERT INTO co_mykronus_groups");
			sql.append(" ( group_name,group_desc,company_id,created_by,created_date,created_time,created_timestamp,group_code)");
			sql.append(" VALUES(?,?,?,?,?,?,?,?)");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int ctr=0;
			
			ctr = jdbctm.update(
					connection -> {
						PreparedStatement ps = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
						ps.setString(1,group_name );
						ps.setString(2,  group_desc );
						ps.setInt(3, companyId);
						ps.setInt(4,userId);
						ps.setDate(5, Utilcollection.getDate());
						ps.setTime(6,Utilcollection.getTime() );
						ps.setTimestamp(7, Utilcollection.getTimeStamp());
						ps.setString(8, group_code);
						
						
						
						
						return ps;
					}, keyHolder);
			System.out.println("-------yuc"+keyHolder.getKey().intValue());
			gm.setGroup_id(keyHolder.getKey().intValue());
		} catch (Exception e) {
			System.out.println(e);
		}
		List<GroupModel> createGroupDetails = listGroup(gm);
		return createGroupDetails;
	}

	public List<GroupModel> updateGroup(GroupModel gm) {
		int userId = gm.getUser_id();
//		int compabyId = gm.getCompany_id();
		String localoffsetTimeZone = gm.getLocalOffsetTime() !=null ? gm.getLocalOffsetTime() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		
		int groupId = gm.getGroup_id();
		String group_code=(gm.getGroup_code()!=null && !gm.getGroup_code().equals("")) ? gm.getGroup_code() :"";
		String group_name=(gm.getGroup_name()!=null && !gm.getGroup_name().equals("")) ? gm.getGroup_name() :"";
		String group_desc=(gm.getGroup_desc()!=null && !gm.getGroup_desc().equals("")) ? gm.getGroup_desc() :"";
		List<GroupModel> updateGroupDetails=null;
		
		StringBuilder sql=new StringBuilder();
		try {
			 sql.append(" UPDATE co_mykronus_groups SET");
			 sql.append(" group_name='"+group_name+"'");
			 sql.append(" ,group_desc='"+group_desc+"'");
			 sql.append(" ,last_updated_by='"+userId+"'");
			 sql.append(" ,last_updated_date='"+Utilcollection.getDate()+"'");
			 sql.append(" ,last_updated_timestamp='"+Utilcollection.getTimeStamp()+"'");
			 sql.append(" ,group_code='"+group_code+"'");
			 sql.append(" WHERE group_id="+groupId);
			 jdbctm.update(sql.toString());
			 updateGroupDetails = listGroup(gm);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		 
		 
		
		return updateGroupDetails;
	}

	public List<GroupModel> deleteGroup(GroupModel gm) {
		
		int userId=gm.getUser_id();
		int groupId=gm.getGroup_id();
		String localoffsetTimeZone = gm.getLocalOffsetTime() !=null ? gm.getLocalOffsetTime() :"";
		if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		}
		localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		List<GroupModel> deleteGroupDetails=null;
		
		StringBuilder sql=new StringBuilder();

		try {
			sql.append(" UPDATE co_mykronus_groups SET status='N' ");
			sql.append(" ,last_updated_by='"+userId+"' ");
			sql.append(" ,last_updated_date='"+Utilcollection.getDate()+"' ");
			sql.append(" ,last_updated_timestamp='"+Utilcollection.getTimeStamp()+"' ");
			sql.append(" WHERE group_id="+groupId);
			jdbctm.update(sql.toString());
			deleteGroupDetails= listGroup(gm);
			 

		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		
		
		
		return deleteGroupDetails;
	}
	
	
	
	
}
