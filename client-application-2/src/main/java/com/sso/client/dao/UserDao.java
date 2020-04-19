package com.sso.client.dao;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sso.client.model.User;

@Repository
public class UserDao {
	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private OAuth2RestOperations restOperations;

	public int create(User user) {
		try {
			Name dn = new LdapName("uid=" + user.getUid() + ",ou=people");
			ldapTemplate.bind(dn, null, buildAttributes(user));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//			headers.setBearerAuth(token);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("uid", user.getUid());
			map.add("cn", user.getFullName());
			map.add("sn", user.getLastName());
			map.add("userPassword", user.getPassword());
//			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//			ResponseEntity<String> response = restTemplate.postForEntity(
//					  "http://localhost:8080/auth/createUser", request , String.class);
			ResponseEntity<String> response = restOperations.postForEntity("http://localhost:8080/auth/createUser",
					request, String.class);
			return response.getStatusCodeValue();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private Attributes buildAttributes(User p) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("person");
		ocAttr.add("organizationalPerson");
		ocAttr.add("inetOrgPerson");
		attrs.put(ocAttr);
		attrs.put("ou", "people");
		attrs.put("uid", p.getUid());
		attrs.put("cn", p.getFullName());
		attrs.put("sn", p.getLastName());
		attrs.put("userPassword", p.getPassword());
		System.out.println(attrs.toString());
		return attrs;
	}
}
