package com.sso.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServer {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServer.class, args);
    }
}
