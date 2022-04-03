package com.Spring2022CSE3311.shoppinglist;


// THESE CLASSES ARE TEMPORARY UNTIL WE GET ROOM WORKING
public class Item {
    private String name;
    private Category category;
    private int amount;

    public Item(String name, Category category) {
        this.name = name;
        this.category = category;
        this.amount = 1;
    }

    public Item(String name, int amount, Category category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
