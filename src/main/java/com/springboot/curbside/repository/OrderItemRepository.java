package com.springboot.curbside.repository;

import com.springboot.curbside.entity.OrderItem;
import com.springboot.curbside.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByCustomerIdAndOrderId(long customerId, long orderId);
}
