package com.Spring2022CSE3311.shoppinglist;

import java.util.Comparator;

public class Item {

    private int id;
    private String itemName;
    private Category itemCategory;
    private int listID;

    public Item(int id, String itemName, Category itemCategory, int listID) {
        this.id = id;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.listID = listID;
    }

    public Item(String itemName, Category itemCategory, int listID) {
        this.id = -1;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.listID = listID;
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

    // Sorts Items by category then by name for now.
    static class ItemComparator implements Comparator<Item>{

        @Override
        public int compare(Item item, Item t1) {
            int catCompare = item.getItemCategory().getCategoryName().compareTo(t1.getItemCategory().getCategoryName());
            int nameCompare = item.getItemName().compareTo(t1.getItemName());
            return (catCompare == 0) ? nameCompare : catCompare;
        }
    }
}
