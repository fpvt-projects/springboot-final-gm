package com.gm.payload.apipayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashInResponse {
    private String email;
    private String channel;
    private double amount;
}
