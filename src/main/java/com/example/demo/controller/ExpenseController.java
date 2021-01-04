package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExpenseModel;
import com.example.demo.service.ExpenseService;

@RestController
@RequestMapping(value="mykronus/expenses")
public class ExpenseController {
	@Autowired
	ExpenseService es;
	
	@PostMapping("/expenselist")
	public List<ExpenseModel> expenselist(@RequestBody ExpenseModel em){
		List<ExpenseModel> expenselist=es.expenselist(em);  
		return expenselist;		
	}
	@PostMapping("/updateexpense")
	public List<ExpenseModel> updateexpense(@RequestBody ExpenseModel em){
		List<ExpenseModel> updateexpense=es.updateexpense(em);  
		return updateexpense;		
	}
	@PostMapping("/createexpense")
	public List<ExpenseModel> createexpense(@RequestBody ExpenseModel em){
		List<ExpenseModel> createexpense=es.createexpense(em);  
		return createexpense;		
	}
	@PostMapping("/deleteexpense")
	public List<ExpenseModel> deleteexpense(@RequestBody ExpenseModel em){
		List<ExpenseModel> deleteexpense=es.deleteexpense(em);  
		return deleteexpense;		
	}
}
