package com.gmf.user_management.config.keycloak.service;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KeycloakService {
    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

//    public List<UserRepresentation> getAllUsers() {
//        RealmResource realmResource = keycloak.realm(realm);
//        return realmResource.users().list();
//    }

    public List<String> getUserApplications(String userId) {
        return keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
    }

    public Map<String, Object> getAllUsers(int page, int size) {
        RealmResource realmResource = keycloak.realm(realm);

        int firstResult = (page - 1) * size;
        List<UserRepresentation> users = realmResource.users().list(firstResult, size);
        int totalItems = realmResource.users().count();

        boolean hasPrev = page > 1;
        boolean hasNext = firstResult + size < totalItems;
        int lastPage = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> responseData = new HashMap<>();
        Map<String, Object> headers = new HashMap<>();

        responseData.put("data", users);
        responseData.put("currentPage", page);
        responseData.put("totalItems", totalItems);
        responseData.put("lastPage", lastPage);
        responseData.put("totalItemsPerPage", size);
        responseData.put("hasPrev", hasPrev);
        responseData.put("hasNext", hasNext);

        headers.put("size", size);
        headers.put("page", page);
        headers.put("status", "OK");

        response.put("response", responseData);
        response.put("headers", headers);
        response.put("message", null);
        response.put("time", LocalDateTime.now().toString());

        return response;
    }

}
