package com.frontline.CatalogueService.repository;

import com.frontline.CatalogueService.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
    Item getItemByItemID(String name);
    List<Item> getAllItemByItemName(String name);
    void deleteByItemID(String name);

}
