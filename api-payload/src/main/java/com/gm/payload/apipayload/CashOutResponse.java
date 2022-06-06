package com.gm.payload.apipayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashOutResponse {

    private String email;

    private double balance;

}
