package com.springboot.curbside.repository;

import com.springboot.curbside.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(String category);
    List<Item> findByNameContainingOrName(String keyword, String exactMatch);
}
