package com.springboot.curbside.service;

import com.springboot.curbside.payload.CustomerLoginResultDTO;
import com.springboot.curbside.payload.LoginRequestDTO;

public interface CustomerLoginService {
    CustomerLoginResultDTO customerLogin(LoginRequestDTO loginRequestDto);
}
