package com.springboot.curbside.service.impl;


import com.springboot.curbside.entity.Customer;
import com.springboot.curbside.payload.*;
import com.springboot.curbside.repository.CustomerRepository;
import com.springboot.curbside.service.CustomerLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerLoginServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }


    public CustomerLoginResultDTO customerLogin(LoginRequestDTO loginRequestDto)
    {
        Customer exists = customerRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        CustomerLoginResultDTO response = new CustomerLoginResultDTO();
        if(exists == null)
        {
            response.setStatus("LOGIN_FAILED");
            response.setTimeOfLogin(LocalDateTime.now());
            response.setCustomerDto(null);
            return response;
        }
        CustomerDTO requestingCustomer = mapToDTO(exists);
        response.setStatus("LOGIN_SUCCEEDED");
        response.setTimeOfLogin(LocalDateTime.now());
        response.setCustomerDto(requestingCustomer);
        return response;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPassword(customer.getPassword());
        return customerDto;
    }

    // convert DTO to entity
    private Customer mapToEntity(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        return customer;
    }
}
