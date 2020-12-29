package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.userStatusRepo;
import com.example.demo.model.userModel;

@Service
public class userStatusService {
	
	@Autowired
	userStatusRepo ud;
	
	
	public List<userModel> getUserStatusDetails(userModel user){
		List<userModel> getDetails=ud.getUserStatusDetails(user);
		return getDetails;
	}
	public String updateUserStatus(userModel user){
		String getDetails=ud.updateUserStatus(user);
		return getDetails;
	}
	
	
}
