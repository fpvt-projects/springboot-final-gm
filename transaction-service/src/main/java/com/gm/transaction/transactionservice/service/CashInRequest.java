package com.gm.transaction.transactionservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashInRequest {

    private String email;

    private double balance;

    private String channel;
}
