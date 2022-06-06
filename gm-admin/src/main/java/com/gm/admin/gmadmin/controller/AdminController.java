package com.gm.admin.gmadmin.controller;

import com.gm.payload.apipayload.GetAllActivitiesResponse;
import com.gm.payload.apipayload.GetAllUsersResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final RestTemplate restTemplate;

    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("all-users")
    public GetAllUsersResponse getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<GetAllUsersResponse> entity = new HttpEntity<GetAllUsersResponse>(headers);

        return restTemplate.exchange("http://localhost:8081/user/all", HttpMethod.GET, entity, GetAllUsersResponse.class).getBody();
    }

    @GetMapping("all-logs")
    public GetAllActivitiesResponse getAllActivities() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<GetAllActivitiesResponse> entity = new HttpEntity<GetAllActivitiesResponse>(headers);

        return restTemplate.exchange("http://localhost:8084/activity", HttpMethod.GET, entity, GetAllActivitiesResponse.class).getBody();
    }
}
