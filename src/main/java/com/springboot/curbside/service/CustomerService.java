package com.springboot.curbside.service;



import com.springboot.curbside.payload.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO itemDto);
    CustomerDTO getCustomerById(long id);
    CustomerDTO updateItem(CustomerDTO customerDto, long id);
    void deleteCustomerById(long id);


    List<CustomerDTO> getAllCustomers();

}
