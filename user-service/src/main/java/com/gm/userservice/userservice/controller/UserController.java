package com.gm.userservice.userservice.controller;

import com.gm.payload.apipayload.*;
import com.gm.userservice.userservice.exceptions.UserNotFoundException;
import com.gm.userservice.userservice.exceptions.UserRegistrationException;
import com.gm.userservice.userservice.model.User;
import com.gm.userservice.userservice.repository.UserRepository;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;


@RestController
@RequestMapping("user")
public class UserController {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    private final BasicPasswordEncryptor passwordEncryption = new BasicPasswordEncryptor();

    public UserController(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserRegistrationResponse userRegistration(@Valid @RequestBody UserRegistrationRequest request) throws UserRegistrationException {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegistrationException("User with the email of "+request.getEmail()+" already exist.");
        }

        String encryptedPassword = passwordEncryption.encryptPassword(request.getPassword());

        User registerUser = new User();
        registerUser.setEmail(request.getEmail());
        registerUser.setFirstName(request.getFirstName());
        registerUser.setMiddleName(request.getMiddleName());
        registerUser.setLastName(request.getLastName());
        registerUser.setPassword(encryptedPassword);
        registerUser.setCreatedAt(LocalDateTime.now());

        startingBalance(0, request.getEmail());

        User savedUser = userRepository.save(registerUser);

        return new UserRegistrationResponse(savedUser.getId());
    }

    @PostMapping("authentication")
    public LoginResponse authentication(@Valid @RequestBody LoginRequest request) throws UserNotFoundException {
        if(userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail());
            if(passwordEncryption.checkPassword(request.getPassword(), user.getPassword())) {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setId(user.getId());
                loginResponse.setLastLogin(LocalDateTime.now());

                return loginResponse;
            }
        }
        throw new UserNotFoundException("Email not found");
    }

    public void startingBalance(double amount, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        CashInRequest cashInRequest = new CashInRequest();
        cashInRequest.setAmount(amount);
        cashInRequest.setEmail(email);

        HttpEntity<CashInRequest> entity = new HttpEntity<>(cashInRequest, headers);

        restTemplate.exchange("http://localhost:8082/transaction", HttpMethod.POST, entity, CashInRequest.class).getBody();
    }
}
