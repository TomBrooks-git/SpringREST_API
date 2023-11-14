package com.springboot.curbside.controller;

import com.springboot.curbside.payload.AdministratorLoginResultDTO;
import com.springboot.curbside.payload.CustomerLoginResultDTO;
import com.springboot.curbside.payload.LoginRequestDTO;
import com.springboot.curbside.service.AdministratorLoginService;
import com.springboot.curbside.service.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customerLogin")
public class CustomerLoginController {
    private CustomerLoginService customerLoginService;
    @Autowired
    public CustomerLoginController(CustomerLoginService customerLoginService)
    {
        this.customerLoginService = customerLoginService;
    }

    @GetMapping
    public ResponseEntity<CustomerLoginResultDTO> makeLoginRequest(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(customerLoginService.customerLogin(loginRequestDTO));
    }

}
