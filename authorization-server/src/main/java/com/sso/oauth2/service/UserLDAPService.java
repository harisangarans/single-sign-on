package com.sso.oauth2.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class UserLDAPService {
	public void create(String entryLines) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/users.ldif",true));) {
		    writer.append("\n\n");
		    writer.append(entryLines);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
