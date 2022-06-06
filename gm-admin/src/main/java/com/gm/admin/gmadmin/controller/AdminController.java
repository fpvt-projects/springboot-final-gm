package com.gm.admin.gmadmin.controller;


import com.gm.payload.apipayload.GetAllUsersResponse;
import com.gm.payload.apipayload.UserDetails;
import com.gm.payload.apipayload.UserRegistrationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final RestTemplate restTemplate;

    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("all-users")
    public List<UserDetails> getAllUsers() {
        ResponseEntity<GetAllUsersResponse> entity = restTemplate.getForEntity("http")
    }


}
