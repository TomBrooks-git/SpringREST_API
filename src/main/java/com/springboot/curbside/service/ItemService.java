package com.springboot.curbside.service;

import com.springboot.curbside.entity.Item;
import com.springboot.curbside.payload.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO createItem(ItemDTO itemDto);
    ItemDTO getItemById(long id);
    ItemDTO updateItem(ItemDTO itemDto, long id);
    void deleteItemById(long id);


    List<ItemDTO> getAllItems();
    List<ItemDTO> getItemsByCategory(String category);
    List<ItemDTO> searchItemsByKeyword(String keyword);
}
