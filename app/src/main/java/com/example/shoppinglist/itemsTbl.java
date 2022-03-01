package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class itemsTbl {
    @PrimaryKey @NonNull
    public String item_name="";

    @ColumnInfo(name="category")
    public String category_name;

    public itemsTbl(){
        this.item_name="";
        this.category_name="";
    }
    public itemsTbl(String item, String cat){
        this.item_name=item;
        this.category_name=cat;
    }

    public String getItem_name(){
        return this.item_name;
    }

    public String getCategory_name(){
        return this.category_name;
    }
}


