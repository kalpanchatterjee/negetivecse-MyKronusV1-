package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ExpenseRepo;
import com.example.demo.model.ExpenseModel;

@Service
public class ExpenseService {
	
	@Autowired
	ExpenseRepo er;

	public List<ExpenseModel> expenselist(ExpenseModel em) {
		List<ExpenseModel> expenselist=er.expenselist(em);  
		return expenselist;
	}

	public List<ExpenseModel> updateexpense(ExpenseModel em) {
		List<ExpenseModel> updateexpense=er.updateexpense(em);  
		return updateexpense;	
	}

	public List<ExpenseModel> createexpense(ExpenseModel em) {
		List<ExpenseModel> createexpense=er.createexpense(em);  
		return createexpense;	
	}

	public List<ExpenseModel> deleteexpense(ExpenseModel em) {
		List<ExpenseModel> deleteexpense=er.deleteexpense(em);  
		return deleteexpense;
	}
}
