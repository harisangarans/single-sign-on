package com.sso.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	@Value("${auth.clienId}")
	private String clienId;
	
	@Value("${auth.secretId}")
	private String secretId;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clienId)
                .secret(passwordEncoder.encode(secretId))
                .authorizedGrantTypes("authorization_code", "password", "client_credentials")
                .scopes("user_info", "read", "write")
                .autoApprove(true)
                .redirectUris("http://localhost:8081/client/login/oauth2/code/custom","http://localhost:8082/client/login/oauth2/code/custom");
    }


}
