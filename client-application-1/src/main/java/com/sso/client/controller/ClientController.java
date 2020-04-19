package com.sso.client.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sso.client.service.ClientService;

@Controller
public class ClientController {

	@Autowired
	ClientService service;

	@GetMapping(value = "/")
	private String index(OAuth2AuthenticationToken user, Model model, Principal userP) {
		if (user != null) {
			model.addAttribute("user_name", service.getFullName(user));
		}
		model.addAttribute("users", service.getAllUsers());
		return "index";
	}

}