package com.springboot.curbside.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        name = "orderItem"
)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id", nullable = false)
    private long orderId;

    @Column(name = "customer_id", nullable = false)
    private long customerId;

    @Column(name = "item_id", nullable = false)
    private long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_at_purchase", nullable = false)
    private String priceAtPurchase;

    @Column(name = "discount")
    private String discount = "NONE";
}