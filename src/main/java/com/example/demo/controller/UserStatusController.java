package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserStatusService;

@RestController
@RequestMapping(value="mykronus/userStatus")
public class UserStatusController {
	
	@Autowired
	UserStatusService ud;
	 
	
	@PostMapping("/getUserStatusDetails")
	public List<UserModel> getUserStatusDetails(@RequestBody  UserModel user) {
		List<UserModel> getDetails=ud.getUserStatusDetails(user);  
		return getDetails;	
	}
	@PostMapping("/updateUserStatus")
	public String updateUserStatus(@RequestBody  UserModel user) {
		String getDetails=ud.updateUserStatus(user);  
		return getDetails;	
	}
	
	
	
	
	
	
}
