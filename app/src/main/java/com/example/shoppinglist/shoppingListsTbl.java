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
        this.lists="";
        this.item = "";
        this.itemObtained=false;
    }

    public shoppingListsTbl(String list, String items_name) {
        this.lists=list;
        this.item = items_name;
        this.itemObtained=false;
    }

    public shoppingListsTbl(String list, String items_name, boolean isObtained) {
        this.lists=list;
        this.item = items_name;
        this.itemObtained=isObtained;
    }

    public String getLists() {return this.lists; }
    public String getItem()  {return this.item;}
    public boolean getItemObtained() {return this.itemObtained;}
}
