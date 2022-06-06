package com.gm.api.service.apiservice.controller;

import com.gm.payload.apipayload.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final RestTemplate restTemplate;

    public TransactionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PutMapping("cashIn")
        public CashInResponse cashInBalance(@RequestBody CashInRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CashInRequest> entity = new HttpEntity<CashInRequest>(request, headers);

        return restTemplate.exchange("http://localhost:8082/transaction/cashIn", HttpMethod.PUT, entity, CashInResponse.class).getBody();
    }

    @PutMapping("cashOut")
    public CashOutResponse cashOutBalance(@RequestBody CashInRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CashInRequest> entity = new HttpEntity<CashInRequest>(request, headers);

        return restTemplate.exchange("http://localhost:8082/transaction/cashOut", HttpMethod.PUT, entity, CashOutResponse.class).getBody();

    }

    @PutMapping("transfer")
    public void cashOutBalance(@RequestBody CashTransferRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CashTransferRequest> entity = new HttpEntity<CashTransferRequest>(request, headers);

        restTemplate.exchange("http://localhost:8082/transaction/transfer", HttpMethod.PUT, entity, CashTransferRequest.class).getBody();
    }
}
