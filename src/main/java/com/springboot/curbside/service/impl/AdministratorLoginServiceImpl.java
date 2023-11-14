package com.springboot.curbside.service.impl;


import com.springboot.curbside.entity.Administrator;
import com.springboot.curbside.payload.AdministratorDTO;
import com.springboot.curbside.payload.AdministratorLoginResultDTO;
import com.springboot.curbside.payload.LoginRequestDTO;
import com.springboot.curbside.repository.AdministratorRepository;
import com.springboot.curbside.service.AdministratorLoginService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdministratorLoginServiceImpl implements AdministratorLoginService {

    private AdministratorRepository administratorRepository;


    public AdministratorLoginServiceImpl(AdministratorRepository administratorRepository)
    {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public AdministratorLoginResultDTO administratorLogin(LoginRequestDTO loginRequestDto) {
        Administrator exists = administratorRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        AdministratorLoginResultDTO response = new AdministratorLoginResultDTO();
        if(exists == null)
        {
            response.setStatus("LOGIN_FAILED");
            response.setTimeOfLogin(LocalDateTime.now());
            response.setAdministratorDto(null);
            return response;
        }
        AdministratorDTO requestingAdmin = mapToDTO(exists);
        response.setStatus("LOGIN_SUCCEEDED");
        response.setTimeOfLogin(LocalDateTime.now());
        response.setAdministratorDto(requestingAdmin);

        return response;
    }



    private AdministratorDTO mapToDTO(Administrator administrator) {
        AdministratorDTO administratorDto = new AdministratorDTO();
        administratorDto.setId(administrator.getId());
        administratorDto.setFirstName(administrator.getFirstName());
        administratorDto.setLastName(administrator.getLastName());
        administratorDto.setEmail(administrator.getEmail());
        administratorDto.setPassword(administrator.getPassword());
        return administratorDto;
    }

    // convert DTO to entity
    private Administrator mapToEntity(AdministratorDTO administratorDTO) {
        Administrator administrator = new Administrator();
        administrator.setId(administratorDTO.getId());
        administrator.setFirstName(administratorDTO.getFirstName());
        administrator.setLastName(administratorDTO.getLastName());
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setPassword(administratorDTO.getPassword());
        return administrator;
    }
}
