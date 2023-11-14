package com.springboot.curbside.payload;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
