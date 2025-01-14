package com.gmf.user_management.config.keycloak.configuration;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class KeycloakConfig {
//    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
//    @Value("${keycloak.realm}")
    private String realm;
//    @Value("${keycloak.resource}")
    private String clientId;
//    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

//    @Bean
//    public Keycloak keycloak(){
//        return KeycloakBuilder.builder()
//                .serverUrl(serverUrl)
//                .realm(realm)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .grantType("client_credential")
//                .build();
//    }
}
