package com.example.shoppinglist;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


// THESE CLASSES ARE TEMPORARY UNTIL WE GET ROOM WORKING
public class List {
    ArrayList<Item> items;
    String name;
    String date;

    public List(String name) {
        this.name = name;
        Date today = new Date();
        this.date = "Date Modified\n" + DateFormat.getDateInstance(DateFormat.SHORT).format(today);
        this.items = new ArrayList<Item>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void updateDate() {
        Date today = new Date();
        this.date = "Date Modified\n" + DateFormat.getDateInstance(DateFormat.SHORT).format(today);
    }
}
