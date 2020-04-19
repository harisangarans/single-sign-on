package com.sso.client.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value( "${ldap.partitionSuffix}" )
	private String baseDn;
	
	@Value( "${ldap.url}" )
	private String ldapUrl;
	
	@Value( "${auth.server.exitUrl}" )
	private String exitUrl;
	
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(resource(), oauth2ClientContext);
    }

    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("custom");
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        List<String> scopes = new ArrayList<>(2);
        scopes.add("write");
        scopes.add("read");
        resource.setAccessTokenUri(clientRegistration.getProviderDetails().getTokenUri());
        resource.setClientId(clientRegistration.getClientId());
        resource.setClientSecret(clientRegistration.getClientSecret());
        resource.setScope(scopes);
        return resource;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .and().logout().logoutSuccessUrl(exitUrl).invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
         
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(baseDn);
        return contextSource;
    }
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
