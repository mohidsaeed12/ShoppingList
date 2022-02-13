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
}


