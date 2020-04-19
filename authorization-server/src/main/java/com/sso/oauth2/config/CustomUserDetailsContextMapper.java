package com.sso.oauth2.config;

import java.util.Collection;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import com.sso.oauth2.model.CustomUserDetails;

@Configuration
public class CustomUserDetailsContextMapper extends LdapUserDetailsMapper implements UserDetailsContextMapper {

    @Override
    public LdapUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
    	try {
	        LdapUserDetailsImpl details = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username, authorities);
	        Attributes dataEntry= ctx.getAttributes();
	        String commonName=dataEntry.get("cn").get().toString();
	        return new CustomUserDetails(details,commonName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
        return null;
    }
}