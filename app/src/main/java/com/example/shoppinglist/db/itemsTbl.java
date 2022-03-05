package com.example.shoppinglist.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class itemsTbl {
    @PrimaryKey @NonNull
    public String item_name;

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

    public boolean equals(itemsTbl other){
        return (this.item_name.equals(other.item_name)&&this.category_name.equals(other.category_name));
    }

    public String getItem_name(){
        return this.item_name;
    }

    public String getCategory_name(){
        return this.category_name;
    }

    public itemsTbl getCatAndItem_name(){return new itemsTbl(getCategory_name(),getItem_name());}
}


