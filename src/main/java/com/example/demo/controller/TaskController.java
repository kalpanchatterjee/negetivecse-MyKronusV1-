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
	@PostMapping("/updateTask")
	public List<TaskModel> updateTask(@RequestBody  TaskModel tm) {
		List<TaskModel> cretaedTaskDetails=ts.updateTask(tm);
		return cretaedTaskDetails;
	}
	
	@PostMapping("/createTask")
	public List<TaskModel> createTask(@RequestBody  TaskModel tm) {
		List<TaskModel> cretaedTaskDetails=ts.createTask(tm);
		return cretaedTaskDetails;
	}
	@PostMapping("/taskDetails")
	public List<TaskModel> taskDetails(@RequestBody  TaskModel tm) {
		List<TaskModel> taskDetails=ts.taskDetails(tm);
		return taskDetails;
	}

	@PostMapping("/editTask")
	public List<TaskModel> editTask(@RequestBody  TaskModel tm) {
		List<TaskModel> editedTaskDetails=ts.editTask(tm);
		return editedTaskDetails;
	}

	@PostMapping("/updateTaskValues")
	public List<TaskModel> updateTaskValues(@RequestBody  TaskModel tm) {
		List<TaskModel> updatedTaskValues=ts.updateTaskValues(tm);
		return updatedTaskValues;
	}
	@PostMapping("/updateTaskActualHour")
	public List<TaskModel> updateTaskActualHour(@RequestBody  TaskModel tm) {
		List<TaskModel> updateTaskActualHour=ts.updateTaskActualHour(tm);
		return updateTaskActualHour;
	}

}
