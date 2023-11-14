package com.springboot.curbside.payload;

import lombok.Data;

import java.util.List;

@Data
public class OrderSummaryDTO {
    private List<OrderItemDTO> orderItemsInCart;
    private List<ItemQuantityPairingDTO> itemQuantities;
    private String totalPrice;
}
