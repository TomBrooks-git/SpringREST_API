package com.springboot.curbside.service;


import com.springboot.curbside.payload.AdministratorDTO;


import java.util.List;

public interface AdministratorService {
    AdministratorDTO createAdministrator(AdministratorDTO administratorDto);
    AdministratorDTO getAdministratorById(long id);
    AdministratorDTO updateAdministrator(AdministratorDTO administratorDTO, long id);
    void deleteAdministratorById(long id);

    List<AdministratorDTO> getAllAdministrators();
}
