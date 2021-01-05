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

import com.example.demo.model.ExpenseModel;
import com.example.demo.util.Utilcollection;

@Repository
public class ExpenseRepo {
		
	@Autowired
	JdbcTemplate jdbctm;
	@Value("${com.rs.lighttpd.path}")
	private String lighttpd;
	
	
	public List<ExpenseModel> expenselist(ExpenseModel em) {
		int companyId= em.getCompany_id();
		int category_id=em.getCategory_id()!=0 ? em.getCategory_id() :0 ;
		String localoffsetTimeZone = em.getLocalOffsetTime() !=null ? em.getLocalOffsetTime() :"";
		 String menu = (em.getMenu() !=null && !em.getMenu().equals("")) ? em.getMenu() :"";
		 if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		 }
		 localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		 
		 StringBuilder sql=new StringBuilder();
		 try {
			 
			 sql.append(" SELECT CONCAT(cu.user_first_name,' ',cu.user_last_name)AS created_by,ca.category_id,ca.category_name,IFNULL(ca.category_desc,'') AS category_desc,ca.company_id,ca.category_type, IFNULL(ca.category_code,'') AS category_code,");
			 sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(ca.created_date ,'"+Utilcollection.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%b %d %Y'),'') AS 'created_date',");
			 sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(ca.created_time , '"+Utilcollection.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%h:%i %p'),'') AS 'created_time'");
			 sql.append(" FROM co_mykronus_accounts_category ca");
			 sql.append(" LEFT JOIN co_users cu");
			 sql.append(" ON cu.user_id=ca.created_by");
			 sql.append(" WHERE ca.status='Y' ");
			 if(category_id!=0) {
				 sql.append(" AND ca.category_id="+category_id); 
			 }
			 if(!menu.equals("") && menu.equals("expenses")) {
				 sql.append(" AND ( ca.company_id="+companyId+" OR ca.category_default='Y' ) "); 
			 }else {
				 sql.append(" AND ca.company_id="+companyId);
			 }
			
		} catch (Exception e) {
			System.out.println(e);
		}
		 
		 
		 
		List<ExpenseModel> getListExpense = jdbctm.query(sql.toString(), BeanPropertyRowMapper.newInstance(ExpenseModel.class));
		
		return getListExpense;
	}


	public List<ExpenseModel> updateexpense(ExpenseModel em) {
		int userId = em.getUser_id();
		int category_id=em.getCategory_id()!= 0 ? em.getCategory_id() :0 ;
		String localoffsetTimeZone = em.getLocalOffsetTime() !=null ? em.getLocalOffsetTime() :"";
		 if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		 }
		 localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		 String category_name=(em.getCategory_name()!=null && !em.getCategory_name().equals("")) ? em.getCategory_name() :"";
		 String category_desc=(em.getCategory_desc()!=null && !em.getCategory_desc().equals("")) ? em.getCategory_desc() :"";
		 String category_type=(em.getCategory_type()!=null && !em.getCategory_type().equals("")) ? em.getCategory_type() :"expense";
		 String category_code=(em.getCategory_code()!=null && !em.getCategory_code().equals("")) ? em.getCategory_code() :"";
		 List<ExpenseModel> upadteExpenseDetails = null;
		 StringBuilder sql=new StringBuilder();
		 
		 try {
			 sql.append(" UPDATE co_mykronus_accounts_category SET");
			 sql.append("  category_name='"+category_name+"'");
			 sql.append(" ,category_desc='"+category_desc+"'");
			 sql.append(" ,last_updated_by='"+userId+"'");
			 sql.append(" ,last_updated_date='"+Utilcollection.getDate()+"'");
			 sql.append(" ,last_updated_timestamp='"+Utilcollection.getTimeStamp()+"'");
			 sql.append(" ,category_type='"+category_type+"'");
			 sql.append(" ,category_code='"+category_code+"'");
			 sql.append(" WHERE category_id="+category_id);
			 jdbctm.update(sql.toString());
			 
		} catch (Exception e) {
			System.out.println(e);
		}
		upadteExpenseDetails = expenselist(em);
		return upadteExpenseDetails;
	}


	public List<ExpenseModel> createexpense(ExpenseModel em) {
//		String result="faliure";
		int userId = em.getUser_id()!= 0 ? em.getUser_id() :0 ;
		int companyId =em.getCompany_id()!= 0 ? em.getCompany_id() :0 ;
		String localoffsetTimeZone = em.getLocalOffsetTime() !=null ? em.getLocalOffsetTime() :"";
		 if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		 }
		 localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		 String category_name=(em.getCategory_name()!=null && !em.getCategory_name().equals("")) ? em.getCategory_name() :"";
		 String category_desc=(em.getCategory_desc()!=null && !em.getCategory_desc().equals("")) ? em.getCategory_desc() :"";
		 String category_type=(em.getCategory_type()!=null && !em.getCategory_type().equals("")) ? em.getCategory_type() :"expense";
		 String category_code=(em.getCategory_code()!=null && !em.getCategory_code().equals("")) ? em.getCategory_code() :"";
		 List<ExpenseModel> createExpense = null;
		 StringBuilder sql=new StringBuilder();
		 try {
			 sql.append(" INSERT INTO co_mykronus_accounts_category");
			 sql.append(" ( category_name,category_desc,company_id,created_by,created_date,created_time,created_timestamp,category_type,category_code)");
			 sql.append(" VALUES(?,?,?,?,?,?,?,?,?)");
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 int ctr=0;
			 ctr = jdbctm.update(connection -> {
				 PreparedStatement ps = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1,category_name );
				 ps.setString(2,category_desc );
				 ps.setInt(3,companyId );
				 ps.setInt(4,userId );
				 ps.setDate(5,Utilcollection.getDate()  );
				 ps.setTime(6,Utilcollection.getTime() );
				 ps.setTimestamp(7,Utilcollection.getTimeStamp() );
				 ps.setString(8, category_type);
				 ps.setString(9, category_code);
				 return ps;
			 },keyHolder);
			 em.setCategory_id(keyHolder.getKey().intValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		 createExpense = expenselist(em);
		return createExpense;
	}


	public List<ExpenseModel> deleteexpense(ExpenseModel em) {
//		String result = "faliure";
		List<ExpenseModel> deleteExpense = null;
		String localoffsetTimeZone = em.getLocalOffsetTime() !=null ? em.getLocalOffsetTime() :"";
		 if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		 }
		localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		int category_id=em.getCategory_id()!= 0 ? em.getCategory_id() :0 ;
		
		
		StringBuilder sql=new StringBuilder();
		
		try {
			sql.append(" UPDATE co_mykronus_accounts_category SET status='N'  WHERE category_id="+category_id );
			jdbctm.update(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		deleteExpense = expenselist(em);
		
		return deleteExpense;
	}
}
