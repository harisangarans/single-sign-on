package com.sso.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sso.client.model.User;
import com.sso.client.service.ClientService;

@Controller
public class ClientController {

    @Autowired
	ClientService service;
    @GetMapping(value="/")
	private String index(OAuth2AuthenticationToken user,Model model) {
		if(user!=null) {
			model.addAttribute("user_name", service.getFullName(user));
			model.addAttribute("USER",new User());
		}
		return "index";
	}
	@PostMapping(value="/")
	private String createUser(OAuth2AuthenticationToken user,@ModelAttribute(value="USER") User u,Model model) {
//		OAuth2AuthenticationDetails auth2AuthenticationDetails=(OAuth2AuthenticationDetails) user.getDetails();
		int response=service.createUser(u);
		model.addAttribute("user_name", service.getFullName(user));
		if(response==200) {
			model.addAttribute("code", response);
			model.addAttribute("USER",new User());
		}else {
			model.addAttribute("code", response);
		}
		return "index";
	}

}