package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserStatusRepo;
import com.example.demo.model.UserModel;

@Service
public class UserStatusService {
	
	@Autowired
	UserStatusRepo ud;
	
	
	public List<UserModel> getUserStatusDetails(UserModel user){
		List<UserModel> getDetails=ud.getUserStatusDetails(user);
		return getDetails;
	}
	public String updateUserStatus(UserModel user){
		String getDetails=ud.updateUserStatus(user);
		return getDetails;
	}
	
	
}
