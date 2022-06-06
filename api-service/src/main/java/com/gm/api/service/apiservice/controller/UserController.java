package com.gm.api.service.apiservice.controller;

import com.gm.payload.apipayload.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("user")
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("registration")
    public UserRegistrationResponse cashInBalance(@RequestBody UserRegistrationRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserRegistrationRequest> entity = new HttpEntity<UserRegistrationRequest>(request, headers);

        return restTemplate.exchange("http://localhost:8081/user", HttpMethod.POST, entity, UserRegistrationResponse.class).getBody();
    }


    @PostMapping("authentication")
    public LoginResponse cashInBalance(@RequestBody LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<LoginRequest> entity = new HttpEntity<LoginRequest>(request, headers);

        return restTemplate.exchange("http://localhost:8081/user/authentication", HttpMethod.POST, entity, LoginResponse.class).getBody();
    }
}