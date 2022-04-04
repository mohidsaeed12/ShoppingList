package com.Spring2022CSE3311.shoppinglist;

import java.util.List;

public class CombiList {
    private SList list;
    private List<Item> items;

    public CombiList(SList list, List<Item> items) {
        this.list = list;
        this.items = items;
    }

    public CombiList() {
    }

    public SList getList() {
        return list;
    }

    public void setList(SList list) {
        this.list = list;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CombiList{" +
                "list=" + list +
                ", items=" + items +
                '}';
    }
}
