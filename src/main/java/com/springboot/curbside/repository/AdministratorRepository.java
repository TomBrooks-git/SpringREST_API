package com.springboot.curbside.repository;

import com.springboot.curbside.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findByEmailAndPassword(String email, String password);
}
