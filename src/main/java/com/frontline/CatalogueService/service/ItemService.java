package com.frontline.CatalogueService.service;

import com.frontline.CatalogueService.model.Item;
import com.frontline.CatalogueService.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public List<Item> getItemByName(String name){
        return itemRepository.getAllItemByItemName(name);
    }

    public Item getItemBYItemID(String itemID){
        return itemRepository.getItemByItemID(itemID);

    }

    public Item deleteItem(String itemID){
        Item item = itemRepository.getItemByItemID(itemID);
        itemRepository.delete(item);
        return item;
    }

    public Item addItem(Item item){
        return itemRepository.saveAndFlush(item);
    }

    public Item updateItem(String itemID, Item item){
        Item existingItem = itemRepository.getItemByItemID(itemID);
        BeanUtils.copyProperties(item,existingItem);
        return itemRepository.saveAndFlush(existingItem);
    }

    public Item decrementQuantity(String itemID, int number){
        Item existingItem = itemRepository.getItemByItemID(itemID);
        existingItem.setQuantity(existingItem.getQuantity()-number);
        return itemRepository.saveAndFlush(existingItem);
    }
}
