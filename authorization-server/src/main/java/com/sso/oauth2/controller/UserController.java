package com.sso.oauth2.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sso.oauth2.service.UserLDAPService;

@RestController
public class UserController {
	@Autowired
	UserLDAPService service;
	
    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
    @PostMapping("/createUser")
    public void createUser(@RequestParam("uid") String uid,@RequestParam("cn") String cn,@RequestParam("sn") String sn,@RequestParam("userPassword") String userPassword) {
    	String entryLines="dn: uid="+uid+",ou=people,dc=springframework,dc=org\n" + 
				"objectclass: top\n" + 
				"objectclass: person\n" + 
				"objectclass: organizationalPerson\n" + 
				"objectclass: inetOrgPerson\n" + 
				"cn: "+cn+"\n" + 
				"sn: "+sn+"\n" + 
				"uid: "+uid+"\n" + 
				"userPassword: "+userPassword+"";
    	System.out.println(entryLines);
    	service.create(entryLines);
    }
}
