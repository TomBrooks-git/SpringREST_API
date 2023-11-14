package com.springboot.curbside.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "ShoppingCart"
)
public class ShoppingCart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(name = "customerId", nullable = false)
    private long customerId;

    @Column(name = "itemId", nullable = false)
    private long itemId;
}
