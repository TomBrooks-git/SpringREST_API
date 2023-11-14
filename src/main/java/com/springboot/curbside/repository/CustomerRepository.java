package com.springboot.curbside.repository;

import com.springboot.curbside.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmailAndPassword(String email, String password);

}
