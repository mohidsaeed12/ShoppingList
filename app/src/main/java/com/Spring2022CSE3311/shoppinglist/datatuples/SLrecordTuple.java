package com.Spring2022CSE3311.shoppinglist.datatuples;

import androidx.room.ColumnInfo;

public class SLrecordTuple {
    @ColumnInfo(name="category") public String category;
    @ColumnInfo(name="item_name") public String item;
    @ColumnInfo(name="obtained") public boolean obtained;

    public SLrecordTuple(String category, String item, boolean obtained){
        this.category=category;
        this.item=item;
        this.obtained=obtained;
    }

    public boolean equals(com.Spring2022CSE3311.shoppinglist.datatuples.SLrecordTuple other){
        return (this.category.equals(other.category)&&this.item.equals(other.item)&&this.obtained==other.obtained);
    }
}
