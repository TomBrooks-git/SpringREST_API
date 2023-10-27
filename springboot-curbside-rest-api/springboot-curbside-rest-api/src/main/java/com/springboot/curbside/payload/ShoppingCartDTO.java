package com.springboot.curbside.payload;


import lombok.Data;

@Data
public class ShoppingCartDTO {
    private long id;
    private long customerId;
    private long itemId;
}