package com.sso.client.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.client.dao.UserDetailsDao;
import com.sso.client.model.User;
@Service
public class ClientService{
	@Autowired
	UserDetailsDao userDetailsDao;
	public String getFullName(OAuth2AuthenticationToken user) {
		ObjectMapper mapper = new ObjectMapper();
		String commonName=mapper.convertValue(user.getPrincipal().getAttributes().get("principal"), HashMap.class).get("commonName").toString();
		return commonName;
	}
	public List<User> getAllUsers(){
		List<User> userList=userDetailsDao.getAllPersonNames();
		userList.stream().forEach(e->e.setFirstName(e.getFullName().split(" ")[0]));
		return userList;
	}
	
}