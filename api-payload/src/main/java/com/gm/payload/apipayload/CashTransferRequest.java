package com.gm.payload.apipayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashTransferRequest {

    @NotBlank
    private String senderEmail;

    @NotBlank
    private String receivingEmail;

    @Min(1)
    private double balance;
}
