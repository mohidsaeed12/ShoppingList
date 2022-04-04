package com.Spring2022CSE3311.shoppinglist;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SList {

    private int id;
    private String listName;
    private String date;

    public SList(int id, String listName, String date) {
        this.id = id;
        this.listName = listName;
        this.date = date;
    }

    public SList() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        Date today = new Date();
        this.date = this.date = "Date Modified\n" + DateFormat.getDateInstance(DateFormat.SHORT).format(today);;
    }

    @Override
    public String toString() {
        return "SList{" +
                "id=" + id +
                ", listName='" + listName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
