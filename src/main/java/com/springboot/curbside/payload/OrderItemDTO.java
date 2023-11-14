package com.springboot.curbside.payload;

import lombok.Data;

@Data
public class OrderItemDTO {
    private long id;
    private long orderId;
    private long itemId;
    private long customerId;
    private String name;
    private String priceAtPurchase;
    private String discount;
}
