package com.example.shoppinglist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class shoppingListsTbl {
    @PrimaryKey
    public int id;

    @ColumnInfo(name="list_names")
    private String lists;

    public String getLists() {
        return lists;
    }

    public void setLists(String lists) {
        this.lists = lists;
    }

    @ColumnInfo(name="item")
    public String item;
}
