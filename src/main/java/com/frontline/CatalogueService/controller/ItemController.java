package com.frontline.CatalogueService.controller;

import com.frontline.CatalogueService.model.Item;
import com.frontline.CatalogueService.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping(value = "/{itemID}")
    public Item getItem(@PathVariable String itemID){
        return itemService.getItemBYItemID(itemID);
    }

    @GetMapping(value = "/name/{itemName}")
    public List<Item> getItemByName(@PathVariable String itemName){
        return itemService.getItemByName(itemName);
    }

    @PostMapping(value = "")
    public Item create(@RequestBody Item newItem){
        return itemService.addItem(newItem);
//        return newItem;
    }

    @PutMapping(value = "/reduce_quantity/{itemID}")
    public Item reduceQuantity(@PathVariable String itemID, @RequestBody Item item){
        return itemService.decrementQuantity(itemID,item.getQuantity());
    }

    @DeleteMapping(value = "/{itemID}")
    public Item deleteItem(@PathVariable String itemID){
        return itemService.deleteItem(itemID);

    }

    @PutMapping(value = "/{itemID}")
    public Item updateItem(@PathVariable String itemID,@RequestBody Item item){
        return itemService.updateItem(itemID,item);
    }
}