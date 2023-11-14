package com.springboot.curbside.service;
import com.springboot.curbside.payload.OrderDTO;
import com.springboot.curbside.payload.OrderItemDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDto);
    OrderDTO getOrderById(long id);
    OrderDTO updateOrder(OrderDTO orderDto, long id);
    void deleteOrderById(long id);

    OrderDTO retrieveCustomerOrderByOrderID(long customerId, String orderId);
    List<OrderDTO> getOrdersByCustomerId(long customerId);

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDto);

    List<OrderDTO> getAllOrders();
}
