package com.springboot.curbside.payload;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AdministratorLoginResultDTO {
    private AdministratorDTO administratorDto;
    private String status;
    private LocalDateTime timeOfLogin;
}
