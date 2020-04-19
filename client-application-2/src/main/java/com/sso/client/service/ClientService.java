package com.sso.client.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.client.dao.UserDao;
import com.sso.client.model.User;
@Service
public class ClientService{
	@Autowired
	UserDao dao;
	
	public String getFullName(OAuth2AuthenticationToken user) {
		ObjectMapper mapper = new ObjectMapper();
		String commonName=mapper.convertValue(user.getPrincipal().getAttributes().get("principal"), HashMap.class).get("commonName").toString();
		return commonName;
	}
	public int createUser(User user) {
		user.setFullName(user.getFirstName()+" "+user.getLastName());
		return dao.create(user);
	}
	
}