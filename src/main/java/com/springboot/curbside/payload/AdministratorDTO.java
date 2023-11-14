package com.springboot.curbside.payload;

import lombok.Data;

@Data
public class AdministratorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
