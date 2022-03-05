package com.example.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import java.util.List;


class item_obtained_tuple{
    @ColumnInfo(name="item_name") public String item;
    @ColumnInfo(name="obtained") public boolean obtained;

    item_obtained_tuple(String item, boolean obtained){
        this.item=item;
        this.obtained=obtained;
    }

    boolean equals(item_obtained_tuple other){
        return (this.item.equals(other.item)&&this.obtained==other.obtained);
    }
}

class SLrecordTuple{
    @ColumnInfo(name="category") public String category;
    @ColumnInfo(name="item_name") public String item;
    @ColumnInfo(name="obtained") public boolean obtained;

    SLrecordTuple(String category, String item, boolean obtained){
        this.category=category;
        this.item=item;
        this.obtained=obtained;
    }

    boolean equals(SLrecordTuple other){
        return (this.category.equals(other.category)&&this.item.equals(other.item)&&this.obtained==other.obtained);
    }
}

@Dao
public interface shoppingListsDAO {
    @Insert(onConflict= OnConflictStrategy.IGNORE) void insert(shoppingListsTbl... shoppingList);
    @Update void update(shoppingListsTbl... shoppingList);
    @Delete void delete(shoppingListsTbl... shoppingList);

    @Query("SELECT DISTINCT list_name FROM shoppingListsTbl")
    LiveData<List<String>> shoppingListNames();

    // Lists intersection of items with a single shopping list
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT itemsTbl.category,itemsTbl.item_name "+
            "FROM itemsTbl INNER JOIN shoppingListsTbl ON shoppingListsTbl.item_name=itemsTbl.item_name "+
            "WHERE shoppingListsTbl.list_name LIKE :selectList")
    LiveData<List<itemsTbl>> itemsByList(String selectList);

    // Lists items at the intersection of a shopping list and a category
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT shoppingListsTbl.item_name, shoppingListsTbl.obtained "+
            "FROM shoppingListsTbl INNER JOIN itemsTbl ON shoppingListsTbl.item_name=itemsTbl.item_name "+
            "WHERE shoppingListsTbl.list_name LIKE :selectList "+
            "AND itemsTbl.category LIKE :selectCategory "+
            "ORDER BY shoppingListsTbl.obtained")
    LiveData<List<item_obtained_tuple>> itemsByListAndCategory(String selectList, String selectCategory);

    @Query("SELECT * FROM shoppingListsTbl")
    LiveData<List<shoppingListsTbl>> shoppingListRecords();
}
