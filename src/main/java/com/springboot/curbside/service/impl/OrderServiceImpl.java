package com.springboot.curbside.service.impl;

import com.springboot.curbside.entity.Order;
import com.springboot.curbside.entity.OrderItem;
import com.springboot.curbside.exception.ResourceNotFoundException;
import com.springboot.curbside.payload.*;
import com.springboot.curbside.repository.OrderItemRepository;
import com.springboot.curbside.repository.OrderRepository;
import com.springboot.curbside.service.OrderService;
import com.springboot.curbside.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    private ShoppingCartService shoppingCartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = mapOrderItemDtoToEntity(orderItemDTO);
        OrderItem newOrder = orderItemRepository.save(orderItem);
        return mapOrderToDTO(newOrder);
    }

// Getting orderItems like above is for aggregating items that were in an order at the price they were at purchase time
    @Override
    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = mapToEntity(orderDto);
        Order newOrder = orderRepository.save(order);
        ShoppingCartSummaryDTO cartAtTimeOfPurchase = shoppingCartService.getShoppingCartSummaryByCustomerId(orderDto.getCustomerId());
        List<ItemInCartDTO> itemsInCart = cartAtTimeOfPurchase.getItemsInCart();
        //We need to insert entries into our order items table
        int i = 0;
        for(ItemInCartDTO item: itemsInCart)
        {
            OrderItemDTO orderItemDto  = new OrderItemDTO();
            orderItemDto.setName(item.getName());
            orderItemDto.setDiscount(item.getDiscount());
            orderItemDto.setCustomerId(orderDto.getCustomerId());
            orderItemDto.setItemId(item.getId());
            orderItemDto.setOrderId(newOrder.getId());
            orderItemDto.setPriceAtPurchase(item.getPrice());
            OrderItem orderItem = mapOrderItemDtoToEntity(orderItemDto);
            orderItemRepository.save(orderItem);
        }
        return mapToDTO(newOrder);
    }

    @Override
    public OrderDTO getOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToDTO(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDto, long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setCustomerId(orderDto.getCustomerId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPickupTime(orderDto.getPickupTime());
        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    //need to implement routine that deletes all entries in orderItems by orderId
    @Override
    public void deleteOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
    }

    //this needs to be changed
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> returnList = new ArrayList<>();
        for(Order order: orders)
        {
            returnList.add(mapToDTO(order));
        }
        return returnList;
    }

    public List<OrderDTO> getOrdersByCustomerId(long customerId){
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        List<OrderDTO> returnList = new ArrayList<>();
        for(Order order: orders)
        {
            returnList.add(retrieveCustomerOrderByOrderID(customerId, String.valueOf(order.getId())));
        }
        return returnList;
    }


    public OrderDTO retrieveCustomerOrderByOrderID(long customerId, String orderId){
        OrderDTO returnOrderDTO = new OrderDTO();
        OrderSummaryDTO orderSummaryDto = new OrderSummaryDTO();
        OrderDTO orderDto = this.getOrderById(Long.parseLong(orderId));
        returnOrderDTO.setOrderDate(orderDto.getOrderDate());
        returnOrderDTO.setPickupTime(orderDto.getPickupTime());

        long intOrderId = Long.parseLong(orderId);
        List<OrderItem> itemsInOrder = orderItemRepository.findByCustomerIdAndOrderId(customerId, intOrderId);
        List<OrderItemDTO> itemDtosInOrder = new ArrayList<>();
        double total = 0.0;
        for(OrderItem orderItem: itemsInOrder)
        {
            itemDtosInOrder.add(mapOrderToDTO(orderItem));
            double price = 0.0;
            if(orderItem.getDiscount().equalsIgnoreCase("NONE"))
            {
                price = Double.parseDouble(orderItem.getPriceAtPurchase());
            }
            else {
                price = (Double.parseDouble(orderItem.getPriceAtPurchase()) * (1 - Double.parseDouble(orderItem.getDiscount())));
            }
            total+=price;
        }
        total*=1.0825;
        orderSummaryDto.setTotalPrice(String.valueOf(total));
        orderSummaryDto.setOrderItemsInCart(itemDtosInOrder);

        Map<String, Integer> itemQuantityPair = new HashMap<>();
        for(OrderItemDTO orderItem: itemDtosInOrder) {
            if (!itemQuantityPair.containsKey(orderItem.getName())) {
                itemQuantityPair.put(orderItem.getName(), 1);
            } else {
                itemQuantityPair.put(orderItem.getName(), (itemQuantityPair.get(orderItem.getName())) + 1);
            }
        }

        List<ItemQuantityPairingDTO> itemQuantityPairingDTOS = new ArrayList<>();

        Set<String> items = itemQuantityPair.keySet();
        Collection<Integer> values = itemQuantityPair.values();

        Iterator<String> keysIterator = items.iterator();
        Iterator<Integer> valuesIterator = values.iterator();

        for(int i = 0; i < items.size(); i++)
        {
            itemQuantityPairingDTOS.add(new ItemQuantityPairingDTO(keysIterator.next(), valuesIterator.next()));
        }

        orderSummaryDto.setItemQuantities(itemQuantityPairingDTOS);
        returnOrderDTO.setOrderSummaryDto(orderSummaryDto);
        returnOrderDTO.setId(intOrderId);
        returnOrderDTO.setCustomerId(customerId);

        return returnOrderDTO;
    }

//    public List<OrderDTO> retrieveAllCustomerOrders(long customerId) {
//        List<OrderDTO>
//    }





    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(order.getId());
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setPickupTime(order.getPickupTime());
        return orderDto;
    }

    // convert DTO to entity
    private Order mapToEntity(OrderDTO orderDto) {
        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPickupTime(orderDto.getPickupTime());
        return order;
    }


    private OrderItemDTO mapOrderToDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDto = new OrderItemDTO();
        orderItemDto.setName(orderItem.getName());
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setCustomerId(orderItemDto.getCustomerId());
        orderItemDto.setOrderId(orderItem.getOrderId());
        orderItemDto.setItemId(orderItem.getItemId());
        orderItemDto.setPriceAtPurchase(orderItem.getPriceAtPurchase());

        return orderItemDto;
    }

    // convert DTO to entity
    private OrderItem mapOrderItemDtoToEntity(OrderItemDTO orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setName(orderItemDto.getName());
        orderItem.setOrderId(orderItemDto.getOrderId());
        orderItem.setItemId(orderItemDto.getItemId());
        orderItem.setPriceAtPurchase(orderItemDto.getPriceAtPurchase());
        orderItem.setCustomerId(orderItemDto.getCustomerId());

        return orderItem;
    }
}
