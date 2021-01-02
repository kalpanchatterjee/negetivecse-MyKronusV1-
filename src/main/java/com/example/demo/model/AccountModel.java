package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AccountModel {
	
	public String account_id;
	public String account_type;
	public String account_name;
	public String account_desc;
	public String account_loc_latt;
	public String account_doc_exist;
	public String account_loc_long;
	public String account_currency_code;
	public String account_reimburse_flag;
	public String account_loc_address1;
	public String created_by;
	public String account_amount;
	public String company_id;
	public String account_comment;
	public String account_date;
	public String uname;
	public String bbm_pin;
	public String user_timezone;
	public String transaction_date;
	public String account_privacy;
	public String account_time;
	public String category_id;
	public String imageUrl;
	public String category_code;
	public String category_type;
	public String category_name;
	private String user_id;
	private String presentDate;
	private String filterVal;
	private String text;
	private String place;
	private String view;
	private String localoffsetTimeZone;
	private String currencyCode;
	private String transaction_time;
	private String array;
	private String typeOfStatus;
	public String result;

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTypeOfStatus() {
		return typeOfStatus;
	}
	public void setTypeOfStatus(String typeOfStatus) {
		this.typeOfStatus = typeOfStatus;
	}
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	public String getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(String transaction_time) {
		this.transaction_time = transaction_time;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_desc() {
		return account_desc;
	}
	public void setAccount_desc(String account_desc) {
		this.account_desc = account_desc;
	}
	public String getAccount_loc_latt() {
		return account_loc_latt;
	}
	public void setAccount_loc_latt(String account_loc_latt) {
		this.account_loc_latt = account_loc_latt;
	}
	public String getAccount_doc_exist() {
		return account_doc_exist;
	}
	public void setAccount_doc_exist(String account_doc_exist) {
		this.account_doc_exist = account_doc_exist;
	}
	public String getAccount_loc_long() {
		return account_loc_long;
	}
	public void setAccount_loc_long(String account_loc_long) {
		this.account_loc_long = account_loc_long;
	}
	public String getAccount_currency_code() {
		return account_currency_code;
	}
	public void setAccount_currency_code(String account_currency_code) {
		this.account_currency_code = account_currency_code;
	}
	public String getAccount_reimburse_flag() {
		return account_reimburse_flag;
	}
	public void setAccount_reimburse_flag(String account_reimburse_flag) {
		this.account_reimburse_flag = account_reimburse_flag;
	}
	public String getAccount_loc_address1() {
		return account_loc_address1;
	}
	public void setAccount_loc_address1(String account_loc_address1) {
		this.account_loc_address1 = account_loc_address1;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getAccount_amount() {
		return account_amount;
	}
	public void setAccount_amount(String account_amount) {
		this.account_amount = account_amount;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getAccount_comment() {
		return account_comment;
	}
	public void setAccount_comment(String account_comment) {
		this.account_comment = account_comment;
	}
	public String getAccount_date() {
		return account_date;
	}
	public void setAccount_date(String account_date) {
		this.account_date = account_date;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getBbm_pin() {
		return bbm_pin;
	}
	public void setBbm_pin(String bbm_pin) {
		this.bbm_pin = bbm_pin;
	}
	public String getUser_timezone() {
		return user_timezone;
	}
	public void setUser_timezone(String user_timezone) {
		this.user_timezone = user_timezone;
	}
	public String getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getAccount_privacy() {
		return account_privacy;
	}
	public void setAccount_privacy(String account_privacy) {
		this.account_privacy = account_privacy;
	}
	public String getAccount_time() {
		return account_time;
	}
	public void setAccount_time(String account_time) {
		this.account_time = account_time;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPresentDate() {
		return presentDate;
	}
	public void setPresentDate(String presentDate) {
		this.presentDate = presentDate;
	}
	public String getFilterVal() {
		return filterVal;
	}
	public void setFilterVal(String filterVal) {
		this.filterVal = filterVal;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getLocaloffsetTimeZone() {
		return localoffsetTimeZone;
	}
	public void setLocaloffsetTimeZone(String localoffsetTimeZone) {
		this.localoffsetTimeZone = localoffsetTimeZone;
	}
	
	
}
