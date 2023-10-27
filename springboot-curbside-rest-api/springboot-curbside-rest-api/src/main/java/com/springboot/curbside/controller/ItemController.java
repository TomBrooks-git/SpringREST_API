package com.springboot.curbside.controller;


import com.springboot.curbside.payload.ItemDTO;
import com.springboot.curbside.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //create new item
    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDto){
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.CREATED);
    }

    //get all items
    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/category")
    public ResponseEntity<List<ItemDTO>> getItemsByCategory(@RequestParam(value="category") String category){
        return ResponseEntity.ok(itemService.getItemsByCategory(category));
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<ItemDTO>> getItemsByKeywordSearch(@RequestParam(value="keyword") String keyword) {
        return ResponseEntity.ok(itemService.searchItemsByKeyword(keyword));
    }

    // get item by id
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    //update item by id
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDto, @PathVariable(name = "id") long id) {
        ItemDTO itemResponse = itemService.updateItem(itemDto, id);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    //delete item by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") long id) {
        itemService.deleteItemById(id);
        return new ResponseEntity<>("Item entity deleted successfully.", HttpStatus.OK);
    }

}
