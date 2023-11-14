package com.springboot.curbside.service;

import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.payload.ItemDTO;
import com.springboot.curbside.payload.ShoppingCartDTO;
import com.springboot.curbside.payload.ShoppingCartSummaryDTO;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDto);
    ShoppingCartDTO getShoppingCartById(long id);
    ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDto, long id);
    void deleteShoppingCartById(long id);

    List<ShoppingCartDTO> getShoppingCartByCustomerId(long customerId);
    ShoppingCartSummaryDTO getShoppingCartSummaryByCustomerId(long customerId);
    void removeAllItemsFromCustomerCart(long customerId);

}
