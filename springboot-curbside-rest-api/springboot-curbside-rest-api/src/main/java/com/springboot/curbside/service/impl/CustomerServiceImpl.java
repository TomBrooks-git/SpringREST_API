package com.springboot.curbside.service.impl;

import com.springboot.curbside.entity.Customer;
import com.springboot.curbside.exception.ResourceNotFoundException;
import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.repository.CustomerRepository;
import com.springboot.curbside.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerDTO createCustomer(CustomerDTO itemDto) {
        Customer customer = mapToEntity(itemDto);
        Customer newCustomer = customerRepository.save(customer);
        return mapToDTO(newCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));;
        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO updateItem(CustomerDTO customerDto, long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));;
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomerById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customerRepository.delete(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> returnList = new ArrayList<>();
        for(Customer customer: customers)
        {
            returnList.add(mapToDTO(customer));
        }
        return returnList;
    }


    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }

    // convert DTO to entity
    private Customer mapToEntity(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }
}
