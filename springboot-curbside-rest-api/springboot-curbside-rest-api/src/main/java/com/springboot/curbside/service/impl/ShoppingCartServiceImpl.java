package com.springboot.curbside.service.impl;

import com.springboot.curbside.entity.ShoppingCart;
import com.springboot.curbside.exception.ResourceNotFoundException;
import com.springboot.curbside.payload.*;
import com.springboot.curbside.repository.CustomerRepository;
import com.springboot.curbside.repository.ShoppingCartRepository;
import com.springboot.curbside.service.CustomerService;
import com.springboot.curbside.service.ItemService;
import com.springboot.curbside.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    private ItemService itemService;
    private CustomerService customerService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ItemService itemService, CustomerService customerService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.itemService = itemService;
        this.customerService = customerService;
    }

    @Override
    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDto) {
        ItemDTO itemDto = itemService.getItemById(shoppingCartDto.getItemId());
        if(itemDto == null)
        {
            throw new ResourceNotFoundException("Item not found with ID: ","Item",shoppingCartDto.getItemId());
        }
        CustomerDTO customerDto = customerService.getCustomerById(shoppingCartDto.getItemId());
        if(customerDto == null)
        {
            throw new ResourceNotFoundException("Customer not found with ID: ","Customer",shoppingCartDto.getCustomerId());
        }
        itemDto.setQuantity(itemDto.getQuantity() - 1);
        itemService.updateItem(itemDto, itemDto.getId());
        ShoppingCart shoppingCart = mapToEntity(shoppingCartDto);
        ShoppingCart newShoppingCart = shoppingCartRepository.save(shoppingCart);
        return mapToDTO(newShoppingCart);
    }

    @Override
    public ShoppingCartDTO getShoppingCartById(long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "id", id));
        return mapToDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDto, long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "id", id));
        shoppingCart.setItemId(shoppingCartDto.getItemId());
        shoppingCart.setCustomerId(shoppingCartDto.getCustomerId());
        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return mapToDTO(updatedShoppingCart);
    }

    @Override
    public void deleteShoppingCartById(long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", "id", id));
        ItemDTO itemDto = itemService.getItemById(shoppingCart.getItemId());
        itemDto.setQuantity(itemDto.getQuantity() + 1);
        itemService.updateItem(itemDto, itemDto.getId());
        shoppingCartRepository.delete(shoppingCart);
    }

    @Override
    public List<ShoppingCartDTO> getShoppingCartByCustomerId(long customerId) {
        List<ShoppingCart> fullCart = shoppingCartRepository.findByCustomerId(customerId);
        List<ShoppingCartDTO> returnCart = new ArrayList<ShoppingCartDTO>();
        for(ShoppingCart shoppingCart: fullCart)
        {
            returnCart.add(mapToDTO(shoppingCart));
        }
        return returnCart;
    }
    @Override
    public ShoppingCartSummaryDTO getShoppingCartSummaryByCustomerId(long customerId) {
        List<ItemInCartDTO> itemsInCart = new ArrayList<>();
        List<ShoppingCartDTO> itemIds = this.getShoppingCartByCustomerId(customerId);


        Map<String, Integer> itemQuantityPair = new HashMap<>();
        for(ShoppingCartDTO itemDTO: itemIds)
        {
            ItemDTO item = itemService.getItemById(itemDTO.getItemId());
            ItemInCartDTO itemInCart = new ItemInCartDTO();

            itemInCart.setId(item.getId());
            itemInCart.setCartId(itemDTO.getId());
            itemInCart.setCategory(item.getCategory());
            itemInCart.setDescription(item.getDescription());
            itemInCart.setQuantity(item.getQuantity());
            itemInCart.setName(item.getName());
            itemInCart.setImgURL(item.getImgURL());
            itemInCart.setPrice(item.getPrice());
            itemsInCart.add(itemInCart);

            if(!itemQuantityPair.containsKey(item.getName())){
                itemQuantityPair.put(item.getName(), 1);
            }
            else{
                itemQuantityPair.put(item.getName(), (itemQuantityPair.get(item.getName())) + 1);
            }
        }

        Set<String> items = itemQuantityPair.keySet();
        Collection<Integer> values = itemQuantityPair.values();

        Iterator<String> keysIterator = items.iterator();
        Iterator<Integer> valuesIterator = values.iterator();

        List<ItemQuantityPairingDTO> itemQuantityPairingDTOS = new ArrayList<>();
        for(int i = 0; i < items.size(); i++)
        {
            itemQuantityPairingDTOS.add(new ItemQuantityPairingDTO(keysIterator.next(), valuesIterator.next()));
        }
        double totalPrice = calculateDollarValueOfCart(itemsInCart);
        ShoppingCartSummaryDTO summaryDTO = new ShoppingCartSummaryDTO();
        summaryDTO.setItemsInCart(itemsInCart);
        summaryDTO.setTotalPrice(totalPrice);
        summaryDTO.setItemQuantities(itemQuantityPairingDTOS);
        return summaryDTO;
    }

    private double calculateDollarValueOfCart(List<ItemInCartDTO> items){
        double totalAmount = 0.0;
        for(ItemInCartDTO item: items){
            totalAmount+=Double.parseDouble(item.getPrice());
        }

        return totalAmount;
    }

    private ShoppingCartDTO mapToDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDto = new ShoppingCartDTO();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setCustomerId(shoppingCart.getCustomerId());
        shoppingCartDto.setItemId(shoppingCart.getItemId());
        return shoppingCartDto;
    }
    // convert DTO to entity
    private ShoppingCart mapToEntity(ShoppingCartDTO shoppingCartDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(shoppingCartDto.getCustomerId());
        shoppingCart.setItemId(shoppingCartDto.getItemId());
        return shoppingCart;
    }

}
