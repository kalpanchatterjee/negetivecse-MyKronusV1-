package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.example.demo.model.AccountModel;
import com.example.demo.util.Utilcollection;

@Repository
public class AccountRepo {


	@Autowired
	JdbcTemplate jdbctm;
	@Value("${com.rs.lighttpd.path}")
	private String lighttpd;

	
	
	public List<AccountModel> getAccountDetails(AccountModel am) {
		String userId="";
		String companyId="";
		String presentDate = (am.getPresentDate()!=null && !am.getPresentDate().equals("")) ? am.getPresentDate() :"";
		String filterVal = (am.getFilterVal()!=null && !am.getFilterVal().equals("")) ? am.getFilterVal() :"";
		String text = (am.getText()!=null && am.getText()!=null && !am.getText().trim().equals("")) ? am.getText().trim() :"";
		String AccountId = (am.getAccount_id()!=null && am.getAccount_id()!=null && !am.getAccount_id().equals("")) ? am.getAccount_id() :"";
		List<AccountModel> getAccountDetails=null;
		userId=am.getUser_id();
		companyId=am.getCompany_id();
		String localoffsetTimeZone = (am.getLocaloffsetTimeZone()!=null && !am.getLocaloffsetTimeZone().equals("")) ? am.getLocaloffsetTimeZone() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		localoffsetTimeZone=Utilcollection.getlocalTimeInHrs(localoffsetTimeZone);
		String place = (am.getPlace()!=null && !am.getPlace().equals("")) ? am.getPlace() :"";
		String view = (am.getView()!=null && !am.getView().equals("")) ?am.getView() :"";
		StringBuilder sql=new StringBuilder();
		StringBuilder sql1= new StringBuilder();
		try {
			sql.append(" SELECT cma.account_id,cma.account_privacy,IFNULL(cma.account_desc,'')AS account_desc,cma.account_type,IFNULL(cma.account_loc_address1,'') AS account_loc_address1,cma.account_name,IFNULL(cma.account_loc_latt,'') AS account_loc_latt,IFNULL(cma.account_loc_long,'') AS account_loc_long ,cma.account_currency_code,cma.account_amount,cma.company_id,IFNULL(cma.account_comment,'') AS account_comment,cma.created_by,cma.account_reimburse_flag,");
			//sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(transaction_date ,'"+UtilCollections.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%b %d %Y'),'') AS 'account_date',");
			//sql.append(" IFNULL( DATE_FORMAT(CONVERT_TZ(transaction_time , '"+UtilCollections.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%h:%i %p'),'') AS 'account_time',");
			//sql.append(" IFNULL(CONVERT_TZ(transaction_date , '"+UtilCollections.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'') AS 'transaction_date'");
			sql.append(" IFNULL(DATE_FORMAT(cma.transaction_date,'%b %d %Y'),'') AS account_date,");
			sql.append(" IFNULL(DATE_FORMAT(cma.transaction_time,'%h:%i %p'),'') AS account_time,");
			sql.append(" DATE_FORMAT(IFNULL((IFNULL(cu.last_updated_timestamp, cu.created_timestamp)), CURRENT_TIMESTAMP),'%y%m%d%H%i%s%f') AS imgTimeStamp,");
			sql.append(" IFNULL(cma.transaction_date,'') AS transaction_date,");
			sql.append(" CONCAT(cu.user_first_name,' ',cu.user_last_name) as uname,cu.user_timezone,IFNULL(cu.user_timezone_intz,'IST') AS bbm_pin,");
			sql.append(" IFNULL(cma.category_id,'') as category_id, IFNULL(cmg.category_code, '') as category_code, IFNULL(cmg.category_name, '') as category_name, IFNULL(cmg.category_type, '') as category_type ");
			sql.append(" FROM  co_mykronus_accounts cma");
			sql.append(" JOIN co_users cu ON cu.user_id=cma.created_by ");
			sql.append(" LEFT JOIN co_mykronus_accounts_category cmg ON cmg.category_id=cma.category_id ");
			sql.append(" WHERE cma.company_id="+companyId);
			if(!view.equals("team")) {
				sql.append(" AND cma.created_by="+userId);
			}else {
				sql.append(" AND cma.account_privacy='business'");
			}
			if(!AccountId.equals("")) {
				sql.append("  AND cma.account_id="+AccountId);
			}
			if(filterVal.equals("Reimbursed")) {
				sql.append(" AND cma.account_reimburse_flag='Y'");
			}
			if(filterVal.equals("Not Reimbursed")) {
				sql.append(" AND cma.account_reimburse_flag='N'");
			}	
			if(!text.equals("")){
				sql.append(" AND (LOWER(cma.account_name) LIKE ('%"+text+"%') ) ");
				sql.append(" OR (LOWER(cma.account_desc) LIKE ('%"+text+"%') ) ");
				sql.append(" OR (LOWER(cma.account_comment) LIKE ('%"+text+"%') ) ");
				sql.append(" OR (DATE_FORMAT(cma.transaction_date,'%b %d %Y') LIKE ('%"+text+"%') ) ");
				sql.append(" OR (LOWER(CONCAT(cu.user_first_name,' ',cu.user_last_name)) LIKE ('%"+text+"%') ) ");
			}
		
			if(place.equals("filterDate")) {
				sql.append(" AND cma.transaction_date='"+presentDate+"'");
			}
			if(!place.equals("filterDate") && AccountId.equals("") ) {
			
				sql.append(" AND ((cma.account_reimburse_flag='N' )");
				sql.append(" OR( cma.account_reimburse_flag='Y' AND (DATE_FORMAT(CONVERT_TZ(cma.account_reimburse_timestamp,'"+Utilcollection.getServerTimeZone()+"','"+localoffsetTimeZone+"'),'%Y-%m-%d')='"+presentDate+"')))");
				
			}
			
			
			
			sql.append(" AND cma.status='Y' ");
			sql.append(" ORDER BY ");
			if(view.equals("team")) {
				sql.append("  CONCAT(cu.user_first_name,' ',cu.user_last_name) ASC ,");	
			}
			sql.append("  IFNULL(category_name,'Z') ASC, CONCAT(cma.transaction_date,' ',cma.transaction_time) DESC ");
			getAccountDetails=jdbctm.query(sql.toString(),BeanPropertyRowMapper.newInstance(AccountModel.class));
			getAccountDetails.forEach(tas->{
				tas.setImageUrl(lighttpd+"/userimages/"+tas.getCreated_by()+".jpeg");
				sql1.delete(0,sql1.length());
				sql1.append(" SELECT COUNT(*) AS account_doc_exist FROM co_mykronus_accounts_document ");
				sql1.append(" WHERE account_id="+tas.getAccount_id());
				sql1.append(" AND status='Y'");
				try {
					tas.setAccount_doc_exist(jdbctm.queryForObject(sql1.toString(), String.class));
				} catch (Exception e) {
					System.out.println(e);
				}
				
			});
		} catch (Exception e) {
			
		}
		return getAccountDetails;
		
	}



	public List<AccountModel> createAccount(AccountModel am) {
		String presentDate = (am.getPresentDate()!=null && !am.getPresentDate().equals("")) ? am.getPresentDate() :"";
		String userId=am.getUser_id();
		String companyId=am.getCompany_id();
		List<AccountModel> createdAccountDetails=null;
		String localoffsetTimeZone = (am.getLocaloffsetTimeZone()!=null && !am.getLocaloffsetTimeZone().equals("")) ? am.getLocaloffsetTimeZone() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		String accountName = (am.getAccount_name()!=null && !am.getAccount_name().equals("")) ? am.getAccount_name() :"";
		String longp = (am.getAccount_loc_long()!=null && !am.getAccount_loc_long().equals("")) ? am.getAccount_loc_long() :"";
		String latp = (am.getAccount_loc_latt()!=null && !am.getAccount_loc_latt().equals("")) ? am.getAccount_loc_latt() :"";
		String currencyCode = (am.getCurrencyCode()!=null && !am.getCurrencyCode().equals("")) ? am.getCurrencyCode() :"";
		String amount = (am.getAccount_amount()!=null && !am.getAccount_amount().equals("")) ? am.getAccount_amount() :"";
		String category_id = (am.getCategory_id()!=null && !am.getCategory_id().equals("")) ? am.getCategory_id() :"";
		String fullAddress = (am.getAccount_loc_address1()!=null && !am.getAccount_loc_address1().equals("")) ? am.getAccount_loc_address1() :"";
		String transaction_date = (am.getTransaction_date()!=null && !am.getTransaction_date().equals("")) ? am.getTransaction_date() :"";
		String transaction_time = (am.getTransaction_time()!=null && !am.getTransaction_time().equals("")) ? am.getTransaction_time() :"";
		String comment = (am.getAccount_comment()!=null && !am.getAccount_comment().equals("")) ? am.getAccount_comment() :"";
		String array = (am.getArray()!=null && !am.getArray().equals("")) ? am.getArray() :"";
		StringBuilder sql=new StringBuilder();
		try {
			sql.append(" INSERT INTO co_mykronus_accounts ");
			sql.append(" (category_id,account_name,account_loc_latt,account_loc_long,account_currency_code,account_amount,company_id,created_by,created_date,created_time,created_timestamp,account_loc_address1,transaction_date,transaction_time,account_comment)");
			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int ctr=0;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbctm.update(
					connection -> {
						PreparedStatement ps = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
						ps.setString(1,category_id );
						ps.setString(2,  accountName );
						ps.setString(3, latp);
						ps.setString(4,longp);
						ps.setString(5,currencyCode);
						ps.setString(6,amount);
						ps.setString(7,companyId);
						ps.setString(8,userId);
						ps.setDate(9, Utilcollection.getDate());
						ps.setTime(10,Utilcollection.getTime());
						ps.setTimestamp(11, Utilcollection.getTimeStamp());
						ps.setString(12,fullAddress);
						ps.setString(13,transaction_date);
						ps.setString(14,transaction_time);
						ps.setString(15,comment);
						return ps;
					}, keyHolder);
			   
			    if(!array.equals("")) {
					 
					String[] arrOfStr = array.split(",", 0); 
					 for(int i=0;i<arrOfStr.length;i++) {
						 StringBuilder sql2=new StringBuilder();
						 String acc_doc_id=arrOfStr[i];
						 sql2.append(" UPDATE co_mykronus_accounts_document SET account_id="+keyHolder.getKey().intValue());
						 sql2.append(" WHERE account_document_id="+acc_doc_id);
						 jdbctm.update(sql2.toString());
					 }
				 }
				am.setAccount_id(String.valueOf(keyHolder.getKey().intValue()));
				createdAccountDetails=getAccountDetails(am);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return createdAccountDetails;
	}



	public List<AccountModel> updateAccount(AccountModel am) {
		String userId=am.getUser_id();
		List<AccountModel> updatedAccountDetails=null;
		String localoffsetTimeZone = (am.getLocaloffsetTimeZone()!=null && !am.getLocaloffsetTimeZone().equals("")) ? am.getLocaloffsetTimeZone() :"";
		if( localoffsetTimeZone.equals("")){
			localoffsetTimeZone="19800";
		}
		String account_name = (am.getAccount_name()!=null && !am.getAccount_name().equals("")) ? am.getAccount_name() :"";
		String lng = (am.getAccount_loc_long()!=null && !am.getAccount_loc_long().equals("")) ? am.getAccount_loc_long() :"";
		String lat = (am.getAccount_loc_latt()!=null && !am.getAccount_loc_latt().equals("")) ? am.getAccount_loc_latt() :"";
		String currency_code = (am.getCurrencyCode()!=null && !am.getCurrencyCode().equals("")) ? am.getCurrencyCode() :"";
		String amount = (am.getAccount_amount()!=null && !am.getAccount_amount().equals("")) ? am.getAccount_amount() :"";
		String category_id = (am.getCategory_id()!=null && !am.getCategory_id().equals("")) ? am.getCategory_id() :"";
		String fullAddress = (am.getAccount_loc_address1()!=null && !am.getAccount_loc_address1().equals("")) ? am.getAccount_loc_address1() :"";
		String transaction_date = (am.getTransaction_date()!=null && !am.getTransaction_date().equals("")) ? am.getTransaction_date() :"";
		String transaction_time = (am.getTransaction_time()!=null && !am.getTransaction_time().equals("")) ? am.getTransaction_time() :"";
		String comment = (am.getAccount_comment()!=null && !am.getAccount_comment().equals("")) ? am.getAccount_comment() :"";
		String account_id = (am.getAccount_id()!=null && am.getAccount_id()!=null && !am.getAccount_id().equals("")) ? am.getAccount_id() :"";
		StringBuilder sql=new StringBuilder();
		try {
			 sql.append(" UPDATE co_mykronus_accounts SET ");
			 sql.append(" account_name='"+account_name+"'");
			 sql.append(" ,category_id='"+category_id+"'");
			 sql.append(" ,account_amount='"+amount+"'");
			 sql.append(" ,account_loc_address1='"+fullAddress+"'");
			 sql.append(" ,transaction_date='"+transaction_date+"'");
			 sql.append(" ,transaction_time='"+transaction_time+"'");
			 sql.append(" ,account_loc_latt='"+lat+"'");
			 sql.append(" ,account_loc_long='"+lng+"'");
			 sql.append(" ,account_comment='"+comment+"'");
			 sql.append(" ,account_currency_code='"+currency_code+"'");
			 sql.append(" ,last_updated_by='"+userId+"'");
			 sql.append(" ,last_updated_date='"+Utilcollection.getDate()+"'");
			 sql.append(" ,last_updated_timestamp='"+Utilcollection.getTimeStamp()+"'");
			 sql.append(" WHERE account_id="+account_id);
			 jdbctm.update(sql.toString());
			 updatedAccountDetails=getAccountDetails(am);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return updatedAccountDetails;
	}



	public List<AccountModel> changeAccountStatus(AccountModel am) {
		String account_id = (am.getAccount_id()!=null && am.getAccount_id()!=null && !am.getAccount_id().equals("")) ? am.getAccount_id() :"";
		StringBuilder sql=new StringBuilder();
		List<AccountModel> changeStatus=new ArrayList<AccountModel>();
		if(am.getTypeOfStatus().equals("delete")) {
			sql.append(" UPDATE co_mykronus_accounts SET status='N'  WHERE account_id="+account_id );
			try{
				jdbctm.update(sql.toString());
				am.setResult("Success");
				changeStatus.add(am);
			}catch(Exception e){
				
			}
			
			
		}else if(am.getTypeOfStatus().equals("reimburse")) {
			String status=am.getAccount_reimburse_flag().equals("Y")?"N":"Y";
			String userId=am.getUser_id();
			try {
				sql.append(" UPDATE co_mykronus_accounts  ");
				sql.append(" SET account_reimburse_flag='"+status+"'");
				sql.append(" , account_reimburse_timestamp=  '"+Utilcollection.getTimeStamp()+"'");
				sql.append(" , last_updated_date= '"+Utilcollection.getDate()+"'");
				sql.append(" , last_updated_timestamp=  '"+Utilcollection.getTimeStamp()+"'");
				sql.append(" , last_updated_by="+userId);
				sql.append("  WHERE account_id="+account_id);
				jdbctm.update(sql.toString());
				changeStatus=getAccountDetails(am);
			} catch (Exception e) {
				
			}
		}
		return changeStatus;
	}
	
	
	
	

}
