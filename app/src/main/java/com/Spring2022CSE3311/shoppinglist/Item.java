package com.Spring2022CSE3311.shoppinglist;

import java.util.Comparator;

public class Item {

    private int id;
    private String itemName;
    int itemQuantity;
    private Category itemCategory;
    private int listID;

    public Item() {}

    public Item(String itemName, int itemQuantity, Category itemCategory, int listID) {
        this();
        this.itemName = itemName;
        this.itemQuantity=itemQuantity;
        this.itemCategory = itemCategory;
        this.listID = listID;
    }

    public Item(int id, String itemName, int itemQuantity, Category itemCategory, int listID) {
        this(itemName,itemQuantity,itemCategory,listID);
        this.id = id;
    }



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

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemCategory=" + itemCategory +
                '}';
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    // Sorts Items 1st by category rank, 2nd by category name, 3rd by item name for now.
    static class ItemComparator implements Comparator<Item>{
        @Override
        public int compare(Item item, Item t1) {
            int catCompare = item.getItemCategory().getCategoryRank()-t1.getItemCategory().getCategoryRank();
            int catNameCompare =item.getItemCategory().getCategoryName().compareTo(t1.getItemCategory().getCategoryName());
            int nameCompare = item.getItemName().compareTo(t1.getItemName());
            return (catCompare == 0) ? ((catNameCompare==0)? nameCompare: catNameCompare) : catCompare;
        }
    }
}
