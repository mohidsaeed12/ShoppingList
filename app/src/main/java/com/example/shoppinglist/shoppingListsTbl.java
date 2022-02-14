package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys={"list_name", "item_name"})
public class shoppingListsTbl {

    @ColumnInfo(name="list_name")   @NonNull public String lists;
    @ColumnInfo(name="item_name")   @NonNull public String item;
    @ColumnInfo(name="obtained")    public boolean itemObtained;

    public shoppingListsTbl() {
        lists="";
        item = "";
        itemObtained=false;
    }
}
