package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AccountModel;
import com.example.demo.service.AccountService;

@RestController
@RequestMapping(value="mykronus/accounts")
public class AccountController {
	
	@Autowired
	AccountService as;
	
	
	@PostMapping("/getAccountDetails")
	public List<AccountModel> getAccountDetails(@RequestBody AccountModel am){
		List<AccountModel> getAccountDetails=as.getAccountDetails(am);  
		return getAccountDetails;		
	}
	@PostMapping("/createAccount")
	public List<AccountModel> createAccount(@RequestBody AccountModel am){
		List<AccountModel> createdAccountDetails=as.createAccount(am);  
		return createdAccountDetails;		
	}
	@PostMapping("/updateAccount")
	public List<AccountModel> updateAccount(@RequestBody AccountModel am){
		List<AccountModel> updatedAccountDetails=as.updateAccount(am);  
		return updatedAccountDetails;		
	}
	@PostMapping("/changeAccountStatus")
	public List<AccountModel> changeAccountStatus(@RequestBody AccountModel am){
		List<AccountModel> changeAccountStatus=as.changeAccountStatus(am);  
		return changeAccountStatus;		
	}
	
}
