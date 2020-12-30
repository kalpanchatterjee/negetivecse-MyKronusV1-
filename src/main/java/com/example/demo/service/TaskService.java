package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TaskRepo;
import com.example.demo.model.TaskModel;

@Service
public class TaskService {

	@Autowired
	TaskRepo tr;
	
	public List<TaskModel> getTaskDetails(TaskModel tm) {
		List<TaskModel> getTaskDetails=tr.getTaskDetails(tm);  
		return getTaskDetails;	
	}

	public List<TaskModel> createTask(TaskModel tm) {
		List<TaskModel> createTask=tr.createTask(tm);  
		return createTask;
	}

	
	
}
