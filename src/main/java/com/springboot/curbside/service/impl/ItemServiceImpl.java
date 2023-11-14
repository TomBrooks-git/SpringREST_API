package com.springboot.curbside.service.impl;

import com.springboot.curbside.entity.Item;
import com.springboot.curbside.exception.ResourceNotFoundException;
import com.springboot.curbside.payload.ItemDTO;
import com.springboot.curbside.repository.ItemRepository;
import com.springboot.curbside.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDTO createItem(ItemDTO itemDto){
        Item item = mapToEntity(itemDto);
        Item newItem = itemRepository.save(item);
        return mapToDTO(newItem);
    }

    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> returnList = new ArrayList<>();
        for(Item item: items)
        {
            returnList.add(mapToDTO(item));
        }
        return returnList;
    }

    public List<ItemDTO> getItemsByCategory(String category) {
        List<Item> items = itemRepository.findByCategory(category);
        List<ItemDTO> returnList = new ArrayList<>();
        for(Item item: items)
        {
            returnList.add(mapToDTO(item));
        }
        return returnList;
    }

    public List<ItemDTO> searchItemsByKeyword(String keyword) {
        List<Item> items = itemRepository.findByNameContainingOrName(keyword, keyword);
        List<ItemDTO> retItems = new ArrayList<>();
        for(Item item: items){
            retItems.add(mapToDTO(item));
        }
        return retItems;
    }
    public ItemDTO getItemById(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        return mapToDTO(item);
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDto, long id) {
        //get post by id from the database, throw exception if it doesn't exist
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setDiscount(itemDto.getDiscount());
        item.setDescription(itemDto.getDescription());
        item.setQuantity(itemDto.getQuantity());
        Item updatedItem = itemRepository.save(item);
        return mapToDTO(updatedItem);
    }

    @Override
    public void deleteItemById(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        itemRepository.delete(item);
    }


    // convert entity to dto
    private ItemDTO mapToDTO(Item item) {
        ItemDTO itemDto = new ItemDTO();
        itemDto.setId(item.getId());
        itemDto.setDiscount(item.getDiscount());
        itemDto.setName(item.getName());
        itemDto.setCategory(item.getCategory());
        itemDto.setImgURL(item.getImgURL());
        itemDto.setPrice(item.getPrice());
        itemDto.setDescription(item.getDescription());
        itemDto.setQuantity(item.getQuantity());
        return itemDto;
    }

    // convert DTO to entity
    private Item mapToEntity(ItemDTO itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDiscount(itemDto.getDiscount());
        item.setCategory(itemDto.getCategory());
        item.setImgURL(itemDto.getImgURL());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        return item;
    }

}
