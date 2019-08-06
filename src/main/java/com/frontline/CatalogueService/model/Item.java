package com.frontline.CatalogueService.model;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String itemID;

    private String itemName;
    private int quantity;
    private double price;

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getNumberOfRaters() {
        return numberOfRaters;
    }

    public void setNumberOfRaters(Integer numberOfRaters) {
        this.numberOfRaters = numberOfRaters;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    private Integer numberOfRaters;
    private Integer rating;

    public Item(){}

    public Item(String itemID,String itemName, int quantity, double price){
        this.itemID =itemID;
        this.itemName=itemName;
        this.quantity=quantity;
        this.price=price;
    }

    public long getId() {
        return id;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
