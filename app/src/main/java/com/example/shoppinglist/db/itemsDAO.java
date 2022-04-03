package com.example.shoppinglist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import java.util.List;

@Dao
public interface itemsDAO {
    @Insert()
    void insert(itemsTbl... items);

    @Update
    void update(itemsTbl... items);

    @Delete
    void delete(itemsTbl item);

    // A simple list of all category names
    @Query("SELECT DISTINCT category FROM itemsTbl")
    LiveData<List<String>> categoryNames();

    // Showing all items and the categories to which they belong
    @Query("SELECT itemsTbl.category,itemsTbl.item_name "+
            "FROM itemsTbl")
    LiveData<List<itemsTbl>> showAllItems();

    // Showing all items and the categories to which they belong
    @Query("SELECT itemsTbl.category,itemsTbl.item_name "+
            "FROM itemsTbl")
    List<itemsTbl> workaroundShowAllItems();

    // Showing all items within the specified category
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT itemsTbl.category, itemsTbl.item_name "+
            "FROM itemsTbl "+
            "WHERE itemsTbl.category LIKE :selectCategory "+
            "ORDER BY itemsTbl.item_name")
    LiveData<List<itemsTbl>> itemsByCategory(String selectCategory);
}
