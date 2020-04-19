package com.sso.client.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import com.sso.client.model.User;
@Repository
public class UserDetailsDao {
	@Autowired
    private LdapTemplate ldapTemplate;

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    public List<User> getAllPersonNames() {
    	return ldapTemplate.findAll(User.class);
    }
}
