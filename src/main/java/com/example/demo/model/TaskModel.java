package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TaskModel {
	private String user_id;
	private String company_id;
	private String task_id;
	private String task_name;
	private String priority_id;
	private String startTime;
	private String endTime;
	private String startDate;
	private String endDate;
	private String detActTime;
	private String actual_time_modified;
	private String elapsed_time;
	private String task_status;
	private String taskComment;
	private String task_billable;
	private String bbm_pin;
	private String user_timezone;
	private String task_type;
	private String taskDueBy;
	private String taskDueDate;
	private String uname;
	private String group_id;
	private String group_code;
	private String group_name;
	private String created_by;
	private String presentDate;
	private String viewForTask;
	private String filterVal;
	private String typeofDate;
	private String sortValue;
	private String text;
	private String imageUrl;
	private String actTimeDetail;
	private String Tasktimestamp;
	private String localoffsetTimeZone;
	private String task_detail_id;
	private String actual_time;
	
	
	public String getTask_detail_id() {
		return task_detail_id;
	}
	public void setTask_detail_id(String task_detail_id) {
		this.task_detail_id = task_detail_id;
	}
	public String getActual_time() {
		return actual_time;
	}
	public void setActual_time(String actual_time) {
		this.actual_time = actual_time;
	}
	public String getLocaloffsetTimeZone() {
		return localoffsetTimeZone;
	}
	public void setLocaloffsetTimeZone(String localoffsetTimeZone) {
		this.localoffsetTimeZone = localoffsetTimeZone;
	}
	public String getTasktimestamp() {
		return Tasktimestamp;
	}
	public void setTasktimestamp(String tasktimestamp) {
		Tasktimestamp = tasktimestamp;
	}
	public String getActTimeDetail() {
		return actTimeDetail;
	}
	public void setActTimeDetail(String actTimeDetail) {
		this.actTimeDetail = actTimeDetail;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getPriority_id() {
		return priority_id;
	}
	public void setPriority_id(String priority_id) {
		this.priority_id = priority_id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDetActTime() {
		return detActTime;
	}
	public void setDetActTime(String detActTime) {
		this.detActTime = detActTime;
	}
	public String getActual_time_modified() {
		return actual_time_modified;
	}
	public void setActual_time_modified(String actual_time_modified) {
		this.actual_time_modified = actual_time_modified;
	}
	public String getElapsed_time() {
		return elapsed_time;
	}
	public void setElapsed_time(String elapsed_time) {
		this.elapsed_time = elapsed_time;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getTaskComment() {
		return taskComment;
	}
	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}
	public String getTask_billable() {
		return task_billable;
	}
	public void setTask_billable(String task_billable) {
		this.task_billable = task_billable;
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
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getTaskDueBy() {
		return taskDueBy;
	}
	public void setTaskDueBy(String taskDueBy) {
		this.taskDueBy = taskDueBy;
	}
	public String getTaskDueDate() {
		return taskDueDate;
	}
	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getPresentDate() {
		return presentDate;
	}
	public void setPresentDate(String presentDate) {
		this.presentDate = presentDate;
	}
	public String getViewForTask() {
		return viewForTask;
	}
	public void setViewForTask(String viewForTask) {
		this.viewForTask = viewForTask;
	}
	public String getFilterVal() {
		return filterVal;
	}
	public void setFilterVal(String filterVal) {
		this.filterVal = filterVal;
	}
	public String getTypeofDate() {
		return typeofDate;
	}
	public void setTypeofDate(String typeofDate) {
		this.typeofDate = typeofDate;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	
}
