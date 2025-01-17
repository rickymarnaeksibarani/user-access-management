package com.gmf.user_management.config.keycloak.controller;

import com.gmf.user_management.config.keycloak.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/keycloak")
@RestController
@Slf4j
public class KeycloakController {

    @Autowired
    private KeycloakService keycloakService;

    @GetMapping("/users")
    public Map<String, Object> getAllUsers(
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size
    ) {
        return keycloakService.getAllUsers(page, size);
    }

    @GetMapping("/users/{userId}/applications")
    public Map<String, Object> getUserApplications(@PathVariable String userId) {
        List<String> applications = keycloakService.getUserApplications(userId);
        return Map.of("userId", userId, "applications", applications);
    }

}

