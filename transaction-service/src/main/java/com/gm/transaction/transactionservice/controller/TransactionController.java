package com.gm.transaction.transactionservice.controller;

import com.gm.payload.apipayload.*;
import com.gm.transaction.transactionservice.model.Transaction;
import com.gm.transaction.transactionservice.repository.TransactionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;

    public TransactionController(RestTemplate restTemplate, TransactionRepository transactionRepository) {
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public void startingBalance(@Valid @RequestBody CashInRequest request) {
        if (transactionRepository.existsByEmail(request.getEmail())) {

        }
        Transaction transaction = new Transaction();
        transaction.setEmail(request.getEmail());
        transaction.setBalance(request.getAmount());

        transactionRepository.save(transaction);
    }

    @PutMapping("cashIn")
    public CashInResponse CashIn(@RequestBody CashInRequest request) {
        if (transactionRepository.existsByEmail(request.getEmail())) {
            if (request.getChannel().equals("OTC") || request.getChannel().equals("TopUp")) {
                Transaction result = transactionRepository.findByEmail(request.getEmail());
                double newBalance = result.getBalance() + request.getAmount();

                result.setBalance(newBalance);
                transactionRepository.save(result);

                logAction("CASH-IN: "+request.getChannel(), request.getEmail());

                return new CashInResponse(result.getEmail(),  request.getChannel(), result.getBalance());
            }
            return null;
        }
        return null;
    }

    @PutMapping("cashOut")
    public CashOutResponse cashOut(@RequestBody CashOutRequest request) {
        if(transactionRepository.existsByEmail(request.getEmail())) {
            Transaction chosenUser = transactionRepository.findByEmail(request.getEmail());
                if(chosenUser.getBalance() >= request.getAmount()) {
                    double newBalance = chosenUser.getBalance() - request.getAmount();
                    chosenUser.setBalance(newBalance);

                    transactionRepository.save(chosenUser);

                    logAction("CASH-OUT", request.getEmail());
                    return new CashOutResponse(chosenUser.getEmail(), chosenUser.getBalance());
                }
            }
        return null;
    }

    @PutMapping("transfer")
    public void cashTransfer(@RequestBody CashTransferRequest request){
        if(transactionRepository.existsByEmail(request.getSenderEmail())) {
            if(transactionRepository.existsByEmail(request.getReceivingEmail())) {

                Transaction senderResult = transactionRepository.findByEmail(request.getSenderEmail());
                Transaction receivingResult = transactionRepository.findByEmail(request.getReceivingEmail());

                //CheckBalance
                double newBalance = senderResult.getBalance() - request.getBalance();
                senderResult.setBalance(newBalance);

                double transferredBalance = receivingResult.getBalance() + request.getBalance();
                receivingResult.setBalance(transferredBalance);

                Transaction doneTransfer = transactionRepository.save(senderResult);
                Transaction doneMinus = transactionRepository.save(receivingResult);

                logAction("CASH-Transfer: "+request.getReceivingEmail(), request.getSenderEmail());
            }
        }
    }

    public void logAction(String action, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ActivityRequest logActivity = new ActivityRequest();
        logActivity.setAction(action);
        logActivity.setEmail(email);

        HttpEntity<ActivityRequest> entity = new HttpEntity<>(logActivity, headers);

        restTemplate.exchange("http://localhost:8084/activity", HttpMethod.POST, entity, ActivityRequest.class).getBody();
    }
}
