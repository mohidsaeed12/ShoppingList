package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName="items")
public class itemsTbl {
    @PrimaryKey @NonNull
    public String item_name="";
    @ColumnInfo(name="category")
    public String category_name;
}


