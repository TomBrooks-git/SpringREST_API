package com.springboot.curbside.payload;

import lombok.Data;

import java.util.List;
@Data
public class ShoppingCartSummaryDTO {
    private List<ItemInCartDTO> itemsInCart;
    private List<ItemQuantityPairingDTO> itemQuantities;
    private double totalPrice;
}
