package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
		int category_id=em.getCategory_id();
		String localoffsetTimeZone = em.getLocalOffsetTime() !=null ? em.getLocalOffsetTime() :"";
		 String menu = (em.getMenu() !=null && !em.getMenu().equals("")) ? em.getMenu() :"";
		 if( localoffsetTimeZone.equals("")){
			 localoffsetTimeZone="19800";
		 }
		 localoffsetTimeZone = Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		 
		 StringBuilder sql=new StringBuilder();
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
		 
		 List<ExpenseModel> getListExpense = jdbctm.query(sql.toString(), BeanPropertyRowMapper.newInstance(ExpenseModel.class));
		
		return getListExpense;
	}
}
