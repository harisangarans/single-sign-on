package com.sso.client.model;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry( base = "ou=people", 
		 objectClasses = { "person", "inetOrgPerson", "top" })
public final class User {
	 	@Id
	    private Name id;
	 	@Attribute(name = "cn")
	 	private String fullName;
	 	@Attribute(name = "uid")
	    private String uid;
	 	@Attribute(name = "sn")
	    private  String lastName;
	 	
	 	private String firstName;
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public Name getId() {
			return id;
		}
		public void setId(Name id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
}
