package com.Spring2022CSE3311.shoppinglist.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class itemsTbl {
    @PrimaryKey @NonNull            public String item_name;
    @ColumnInfo(name="category")    public String category_name;

    // Constructors
    public itemsTbl()                           {this.item_name="";      this.category_name=""; }
    public itemsTbl(String item, String cat)    {this.item_name=item;    this.category_name=cat;}

    // Check to see if the contents of two itemsTbl objects are the same
    public boolean equals(itemsTbl other){
        return (this.item_name.equals(other.item_name)&&
                this.category_name.equals(other.category_name));
    }

    // Getter methods
    public String getItem_name(){
        return this.item_name;
    }

    public String getCategory_name(){
        return this.category_name;
    }

    public itemsTbl getCatAndItem_name(){
        return new itemsTbl(item_name,category_name);
    }
}


