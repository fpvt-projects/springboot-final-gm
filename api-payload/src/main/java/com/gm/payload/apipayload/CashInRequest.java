package com.gm.payload.apipayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashInRequest {

    private String email;

    private double amount;

    private String channel;
}
