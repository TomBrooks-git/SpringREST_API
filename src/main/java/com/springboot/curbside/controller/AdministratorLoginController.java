package com.springboot.curbside.controller;

import com.springboot.curbside.payload.AdministratorLoginResultDTO;
import com.springboot.curbside.payload.LoginRequestDTO;
import com.springboot.curbside.service.AdministratorLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administratorLogin")
public class AdministratorLoginController {

    private AdministratorLoginService administratorLoginService;
    @Autowired
    public AdministratorLoginController(AdministratorLoginService administratorLoginService)
    {
        this.administratorLoginService = administratorLoginService;
    }

    @GetMapping
    public ResponseEntity<AdministratorLoginResultDTO> makeLoginRequest(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(administratorLoginService.administratorLogin(loginRequestDTO));
    }
}
