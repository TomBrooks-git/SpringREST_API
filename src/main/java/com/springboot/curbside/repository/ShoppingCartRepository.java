package com.springboot.curbside.repository;

import com.springboot.curbside.entity.Item;
import com.springboot.curbside.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByCustomerId(long id);
    void deleteByCustomerId(long customerId);
}
