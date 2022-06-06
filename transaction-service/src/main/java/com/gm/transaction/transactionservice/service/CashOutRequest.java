package com.gm.transaction.transactionservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashOutRequest {

    private String email;

    private double amount;
}
