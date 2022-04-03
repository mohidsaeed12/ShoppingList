package com.Spring2022CSE3311.shoppinglist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import com.Spring2022CSE3311.shoppinglist.datatuples.item_obtained_tuple;

import java.util.List;


@Dao
public interface shoppingListsDAO {
    @Insert(onConflict= OnConflictStrategy.IGNORE) void insert(shoppingListsTbl... shoppingList);
    @Update void update(shoppingListsTbl... shoppingList);
    @Delete void delete(shoppingListsTbl... shoppingList);

    // Simple list of shopping list names
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

    // List of all shopping lists and items in them
    @Query("SELECT * FROM shoppingListsTbl")
    LiveData<List<shoppingListsTbl>> shoppingListRecords();
}
