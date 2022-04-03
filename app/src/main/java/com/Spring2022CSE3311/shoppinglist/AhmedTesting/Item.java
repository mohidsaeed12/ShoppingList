package com.Spring2022CSE3311.shoppinglist.AhmedTesting;

public class Item {

    private int id;
    private String itemName;
    private Category itemCategory;

    public Item(int id, String itemName, Category itemCategory) {
        this.id = id;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
    }

    public Item() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemCategory=" + itemCategory +
                '}';
    }
}
