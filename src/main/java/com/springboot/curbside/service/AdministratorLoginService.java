package com.springboot.curbside.service;

import com.springboot.curbside.payload.AdministratorLoginResultDTO;
import com.springboot.curbside.payload.LoginRequestDTO;

public interface AdministratorLoginService {
    AdministratorLoginResultDTO administratorLogin(LoginRequestDTO loginRequestDto);

}
