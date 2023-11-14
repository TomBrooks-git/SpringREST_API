package com.springboot.curbside.controller;

import com.springboot.curbside.entity.Administrator;
import com.springboot.curbside.payload.AdministratorDTO;
import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {
    private AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService)
    {
        this.administratorService = administratorService;
    }

    @PostMapping
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody AdministratorDTO administratorDTO){
        return new ResponseEntity<>(administratorService.createAdministrator(administratorDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<AdministratorDTO> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(administratorService.getAdministratorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@RequestBody AdministratorDTO administratorDTO, @PathVariable(name = "id") long id){
        AdministratorDTO administratorResponse = administratorService.updateAdministrator(administratorDTO, id);
        return new ResponseEntity<>(administratorResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") long id){
        administratorService.deleteAdministratorById(id);
        return new ResponseEntity<>("Administrator entity deleted succesfully", HttpStatus.OK);
    }

}
