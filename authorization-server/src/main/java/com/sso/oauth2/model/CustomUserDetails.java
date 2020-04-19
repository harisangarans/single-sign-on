package com.sso.oauth2.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class CustomUserDetails implements LdapUserDetails {
	private static final long serialVersionUID = 1L;
    private LdapUserDetails details;
    private String commonName;
    public CustomUserDetails(LdapUserDetails details, String commonName) {
        this.details = details;
        this.commonName= commonName;
    }
    @Override
    public String getDn() {
        return details.getDn();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return details.getAuthorities();
    }

    @Override
    public String getPassword() {
        return details.getPassword();
    }

    @Override
    public String getUsername() {
        return details.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return details.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return details.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return details.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return details.isEnabled();
    }

	@Override
	public void eraseCredentials() {
		
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
}
