package com.springboot.curbside.controller;

import com.springboot.curbside.payload.ShoppingCartDTO;
import com.springboot.curbside.payload.ShoppingCartSummaryDTO;
import com.springboot.curbside.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shoppingCarts")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;


    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    //create new item
    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDto){
        return new ResponseEntity<>(shoppingCartService.createShoppingCart(shoppingCartDto), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ShoppingCartSummaryDTO> getShoppingCartSummary(@PathVariable(name="customerId") long id){
        ShoppingCartSummaryDTO shoppingCart = shoppingCartService.getShoppingCartSummaryByCustomerId(id);
        return ResponseEntity.ok(shoppingCart);
    }


    //delete item by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShoppingCartById(@PathVariable(name = "id") long id) {
        shoppingCartService.deleteShoppingCartById(id);
        return new ResponseEntity<>("Item entity deleted successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/emptyCart/{customerId}")
    public ResponseEntity<String> deleteShoppingCartEntriesByCustomerId(@PathVariable Long customerId) {
        shoppingCartService.removeAllItemsFromCustomerCart(customerId);
        return ResponseEntity.ok("Shopping cart entries for customer with ID: " + customerId + " have been successfully deleted.");
    }



}
