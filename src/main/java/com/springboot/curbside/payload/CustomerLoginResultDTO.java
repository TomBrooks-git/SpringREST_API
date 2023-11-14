package com.springboot.curbside.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerLoginResultDTO {
    private CustomerDTO customerDto;
    private String status;
    private LocalDateTime timeOfLogin;
}
