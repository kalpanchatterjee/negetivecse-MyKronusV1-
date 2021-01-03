package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GroupModel;
import com.example.demo.service.GroupService;

@RestController
@RequestMapping(value="mykronus/groups")
public class GroupController {
	
	@Autowired
	GroupService gs;
	
	
	@PostMapping("/listGroup")
	public List<GroupModel> listGroup(@RequestBody GroupModel gm){
		List<GroupModel> listGroup=gs.listGroup(gm);  
		return listGroup;		
	}
	@PostMapping("/createGroup")
	public List<GroupModel> createGroup(@RequestBody GroupModel gm){
		List<GroupModel> createGroup=gs.createGroup(gm);  
		return createGroup;		
	}
	@PostMapping("/updateGroup")
	public List<GroupModel> updateGroup(@RequestBody GroupModel gm){
		List<GroupModel> updateGroup=gs.updateGroup(gm);  
		return updateGroup;		
	}
	@PostMapping("/deleteGroup")
	public List<GroupModel> deleteGroup(@RequestBody GroupModel gm){
		List<GroupModel> deleteGroup=gs.deleteGroup(gm);  
		return deleteGroup;		
	}

}
