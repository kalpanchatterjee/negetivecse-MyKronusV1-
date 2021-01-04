package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllUserModel {
	private int USER_ID;
	private String name;
	private String login_name;
	private String user_email;
	private String user_phone_code;
	private String user_phone;
	private String work_number;
	private String user_department;
	private String biography;
	private String user_image_type;
	private String imgTimeStamp;
	private String status;
	private String middle_name;
	private String last_name;
	private String role_id;
	private String role_name;
	private String userUsageTime;
	private String userUsageTime2;
	private String WorkHrs;
	private String BreakHrs;
	private String TotalHrs;
	private String workStatus;
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_phone_code() {
		return user_phone_code;
	}
	public void setUser_phone_code(String user_phone_code) {
		this.user_phone_code = user_phone_code;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getWork_number() {
		return work_number;
	}
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}
	public String getUser_department() {
		return user_department;
	}
	public void setUser_department(String user_department) {
		this.user_department = user_department;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getUser_image_type() {
		return user_image_type;
	}
	public void setUser_image_type(String user_image_type) {
		this.user_image_type = user_image_type;
	}
	public String getImgTimeStamp() {
		return imgTimeStamp;
	}
	public void setImgTimeStamp(String imgTimeStamp) {
		this.imgTimeStamp = imgTimeStamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
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
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	
}
