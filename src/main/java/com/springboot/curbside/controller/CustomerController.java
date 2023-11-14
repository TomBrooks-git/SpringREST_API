package com.springboot.curbside.controller;


import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //create new customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    //get all customers
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //get a customer by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //update customer by id
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDto, @PathVariable(name = "id") long id){
        CustomerDTO customerResponse = customerService.updateItem(customerDto, id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    //delete customer by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") long id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>("Customer entity deleted succesfully", HttpStatus.OK);
    }

}
