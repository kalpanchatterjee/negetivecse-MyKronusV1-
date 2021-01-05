package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.GroupRepo;
import com.example.demo.model.GroupModel;

@Service
public class GroupService {
	
	@Autowired
	GroupRepo gr;

	public List<GroupModel> listGroup(GroupModel gm) {
		List<GroupModel> listGroup=gr.listGroup(gm);  
		return listGroup;
	}

	public List<GroupModel> createGroup(GroupModel gm) {
		List<GroupModel> createGroup=gr.createGroup(gm);  
		return createGroup;	
	}

	public List<GroupModel> updateGroup(GroupModel gm) {
		List<GroupModel> updateGroup=gr.updateGroup(gm);  
		return updateGroup;
	}

	public List<GroupModel> deleteGroup(GroupModel gm) {
		List<GroupModel> deleteGroup=gr.deleteGroup(gm);  
		return deleteGroup;	
	}

}
