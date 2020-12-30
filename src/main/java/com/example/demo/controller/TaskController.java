package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TaskModel;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping(value="mykronus/tasks")
public class TaskController {
	
	
	@Autowired
	TaskService ts;
	
	
	@PostMapping("/getTaskDetails")
	public List<TaskModel> getTaskDetails(@RequestBody TaskModel tm){
		List<TaskModel> getTaskDetails=ts.getTaskDetails(tm);  
		return getTaskDetails;	
		
	}
	
	@PostMapping("/createTask")
	public List<TaskModel> createTask(@RequestBody  TaskModel tm) {
		List<TaskModel> cretaedTaskDetails=ts.createTask(tm);
		return cretaedTaskDetails;
	}
}
