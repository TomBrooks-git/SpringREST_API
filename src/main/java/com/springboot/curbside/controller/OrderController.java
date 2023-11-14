package com.springboot.curbside.controller;

import com.springboot.curbside.payload.CustomerDTO;
import com.springboot.curbside.payload.OrderDTO;
import com.springboot.curbside.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.createOrder(orderDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable(name = "id") long customerId)
    {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/{customerId}/orderId")
    public ResponseEntity<OrderDTO> getCustomerOrderByOrderId(@PathVariable(name = "customerId") long customerId, @RequestParam(value = "orderId") String orderId)
    {
        return new ResponseEntity<>(orderService.retrieveCustomerOrderByOrderID(customerId, orderId), HttpStatus.OK);
    }

}
