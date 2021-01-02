package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountRepo;
import com.example.demo.model.AccountModel;

@Service
public class AccountService {

	@Autowired
	AccountRepo ar;
	
	public List<AccountModel> getAccountDetails(AccountModel am) {
		List<AccountModel> getAccountDetails=ar.getAccountDetails(am);  
		return getAccountDetails;	
	}

	public List<AccountModel> createAccount(AccountModel am) {
		List<AccountModel> createdAccountDetails=ar.createAccount(am);  
		return createdAccountDetails;	
	}

	public List<AccountModel> updateAccount(AccountModel am) {
		List<AccountModel> updatedAccountDetails=ar.updateAccount(am);  
		return updatedAccountDetails;	
	}

	public List<AccountModel> changeAccountStatus(AccountModel am) {
		List<AccountModel> changeAccountStatus=ar.changeAccountStatus(am);  
		return changeAccountStatus;	
	}

}
