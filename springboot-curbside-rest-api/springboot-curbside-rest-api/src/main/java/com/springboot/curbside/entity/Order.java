package com.springboot.curbside.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"shoppingCartId"})}
)
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "totalPrice", nullable = false)
    private String totalPrice;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated;
    @Column(name = "pickupTime", nullable = false)
    private LocalDateTime pickupTime;
}
