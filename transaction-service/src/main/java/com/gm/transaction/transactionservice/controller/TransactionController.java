package com.gm.transaction.transactionservice.controller;

import com.gm.transaction.transactionservice.model.Transaction;
import com.gm.transaction.transactionservice.repository.TransactionRepository;
import com.gm.transaction.transactionservice.service.CashInRequest;
import com.gm.transaction.transactionservice.service.CashInResponse;
import com.gm.transaction.transactionservice.service.CashTransferRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.gm.payload.*;

import javax.validation.Valid;


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
        transaction.setBalance(request.getBalance());

        transactionRepository.save(transaction);
    }

    @PutMapping("cashIn")
    public CashInResponse CashIn(@RequestBody CashInRequest request){
      if(transactionRepository.existsByEmail(request.getEmail())) {
          Transaction result = transactionRepository.findByEmail(request.getEmail());
          double newBalance = result.getBalance() + request.getBalance();

          result.setBalance(newBalance);
          Transaction updatedBalance = transactionRepository.save(result);

          return new CashInResponse(updatedBalance.getBalance());
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
             }
        }
    }

    public void cashTransfer() {
        //TODO - When user with appointed email transfers balance to another appointed email
    }
}
