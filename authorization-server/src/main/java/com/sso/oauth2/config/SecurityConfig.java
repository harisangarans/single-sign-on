package com.sso.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value( "${spring.ldap.embedded.base-dn}" )
	private String baseDn;
	
	@Value( "${spring.ldap.embedded.url}" )
	private String ldapUrl;
	
	@Value( "${spring.ldap.embedded.password-attribute}" )
	private String passwordAttribute;
	
	private static final String USER_DN_PATTERNS="uid={0},ou=people";
	private static final String GROUP_SEARCH_BASE="ou=groups";
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize","/exit")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and().csrf().disable();
    }

	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
	        .userDnPatterns(USER_DN_PATTERNS)
	        .groupSearchBase(GROUP_SEARCH_BASE)
	        .contextSource()
	        .url(ldapUrl+baseDn)
            .and()
	        .passwordCompare()
            .passwordEncoder(new LdapShaPasswordEncoder())
            .passwordAttribute(passwordAttribute)
            .and().userDetailsContextMapper(userDetailsContextMapper());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomUserDetailsContextMapper();
    }
}
