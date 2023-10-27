package com.springboot.curbside.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemQuantityPairingDTO {
    private String itemName;
    private int itemQuantity;
}
