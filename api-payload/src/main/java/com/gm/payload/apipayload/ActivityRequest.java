package com.gm.payload.apipayload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityRequest {
    private String action;
    private String email;
}
