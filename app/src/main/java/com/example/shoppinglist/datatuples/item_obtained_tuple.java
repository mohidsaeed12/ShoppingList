package com.example.shoppinglist.datatuples;

import androidx.room.ColumnInfo;

public class item_obtained_tuple {
    @ColumnInfo(name="item_name") public String item;
    @ColumnInfo(name="obtained") public boolean obtained;

    public item_obtained_tuple(String item, boolean obtained){
        this.item=item;
        this.obtained=obtained;
    }

    public boolean equals(com.example.shoppinglist.datatuples.item_obtained_tuple other){
        return (this.item.equals(other.item)&&this.obtained==other.obtained);
    }
}
