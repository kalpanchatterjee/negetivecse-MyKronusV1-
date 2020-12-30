package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserModel {

	private int id ;
	private String dataType;
	private int user_id; 
	private int company_id; 
	private String userUsageDate; 
	private String userUsageTime; 
	private String userUsageTime2; 
	private String userUsageDateTimestamp; 
	private String lat_position; 
	private String long_position; 
	private String statusComment; 
	private String WorkHrs; 
	private String BreakHrs; 
	private String TotalHrs; 
	private String currElapsedTime; 
	private String bbm_pin; 
	private String user_timezone; 
	private String STATUS;
	private String presentdate;
	private String prevStatus;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String localoffsetTimeZone;
	
	
	public String getLocaloffsetTimeZone() {
		return localoffsetTimeZone;
	}
	public void setLocaloffsetTimeZone(String localoffsetTimeZone) {
		this.localoffsetTimeZone = localoffsetTimeZone;
	}
	public String getPrevHrs() {
		return prevHrs;
	}
	public void setPrevHrs(String prevHrs) {
		this.prevHrs = prevHrs;
	}
	private String zip_code;
	private String country;
	private String prevHrs;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getUserUsageDate() {
		return userUsageDate;
	}
	public void setUserUsageDate(String userUsageDate) {
		this.userUsageDate = userUsageDate;
	}
	public String getUserUsageTime() {
		return userUsageTime;
	}
	public void setUserUsageTime(String userUsageTime) {
		this.userUsageTime = userUsageTime;
	}
	public String getUserUsageTime2() {
		return userUsageTime2;
	}
	public void setUserUsageTime2(String userUsageTime2) {
		this.userUsageTime2 = userUsageTime2;
	}
	public String getUserUsageDateTimestamp() {
		return userUsageDateTimestamp;
	}
	public void setUserUsageDateTimestamp(String userUsageDateTimestamp) {
		this.userUsageDateTimestamp = userUsageDateTimestamp;
	}
	public String getLat_position() {
		return lat_position;
	}
	public void setLat_position(String lat_position) {
		this.lat_position = lat_position;
	}
	public String getLong_position() {
		return long_position;
	}
	public void setLong_position(String long_position) {
		this.long_position = long_position;
	}
	public String getStatusComment() {
		return statusComment;
	}
	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}
	public String getWorkHrs() {
		return WorkHrs;
	}
	public void setWorkHrs(String workHrs) {
		WorkHrs = workHrs;
	}
	public String getBreakHrs() {
		return BreakHrs;
	}
	public void setBreakHrs(String breakHrs) {
		BreakHrs = breakHrs;
	}
	public String getTotalHrs() {
		return TotalHrs;
	}
	public void setTotalHrs(String totalHrs) {
		TotalHrs = totalHrs;
	}
	public String getCurrElapsedTime() {
		return currElapsedTime;
	}
	public void setCurrElapsedTime(String currElapsedTime) {
		this.currElapsedTime = currElapsedTime;
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
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getPresentdate() {
		return presentdate;
	}
	public void setPresentdate(String presentdate) {
		this.presentdate = presentdate;
	}
	public String getPrevStatus() {
		return prevStatus;
	}
	public void setPrevStatus(String prevStatus) {
		this.prevStatus = prevStatus;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	


	
}
