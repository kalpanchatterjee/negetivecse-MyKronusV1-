package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel;
import com.example.demo.service.userStatusService;

@RestController
@RequestMapping(value="mykronus/userStatus")
public class userStatusController {
	
	@Autowired
	userStatusService ud;
	 
	
	@PostMapping("/getUserStatusDetails")
	public List<userModel> getUserStatusDetails(@RequestBody  userModel user) {
		List<userModel> getDetails=ud.getUserStatusDetails(user);  
		return getDetails;	
	}
	@PostMapping("/updateUserStatus")
	public String updateUserStatus(@RequestBody  userModel user) {
		String getDetails=ud.updateUserStatus(user);  
		return getDetails;	
	}
	
	
	
	
	
	
}
