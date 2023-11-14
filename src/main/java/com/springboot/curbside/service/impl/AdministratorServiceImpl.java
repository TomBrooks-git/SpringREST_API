package com.springboot.curbside.service.impl;


import com.springboot.curbside.entity.Administrator;

import com.springboot.curbside.entity.Customer;
import com.springboot.curbside.exception.ResourceNotFoundException;
import com.springboot.curbside.payload.AdministratorDTO;

import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.repository.AdministratorRepository;
import com.springboot.curbside.service.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private AdministratorRepository administratorRepository;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository)
    {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public AdministratorDTO createAdministrator(AdministratorDTO administratorDto) {
        Administrator administrator = mapToEntity(administratorDto);
        Administrator newAdministrator = administratorRepository.save(administrator);
        return mapToDTO(newAdministrator);
    }

    @Override
    public AdministratorDTO getAdministratorById(long id) {
        Administrator administrator = administratorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Administrator", "id", id));;
        return mapToDTO(administrator);
    }

    @Override
    public AdministratorDTO updateAdministrator(AdministratorDTO administratorDTO, long id) {
        Administrator administrator = administratorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));;
        administrator.setFirstName(administratorDTO.getFirstName());
        administrator.setLastName(administratorDTO.getLastName());
        administrator.setEmail(administratorDTO.getEmail());
        Administrator updatedAdministrator = administratorRepository.save(administrator);
        return mapToDTO(updatedAdministrator);
    }

    @Override
    public void deleteAdministratorById(long id) {
        Administrator administrator = administratorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Administrator", "id", id));
        administratorRepository.delete(administrator);
    }


    public List<AdministratorDTO> getAllAdministrators() {
        List<Administrator> administrators = administratorRepository.findAll();
        List<AdministratorDTO> returnList = new ArrayList<>();
        for(Administrator administrator: administrators)
        {
            returnList.add(mapToDTO(administrator));
        }
        return returnList;
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
