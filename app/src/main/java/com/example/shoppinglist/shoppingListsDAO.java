package com.example.shoppinglist;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import java.util.List;

class category_item_tuple {
    @ColumnInfo(name="category")
    public String category;

    @ColumnInfo(name="item_name")
    public String item;
}

class item_obtained_tuple{
    @ColumnInfo(name="item_name")
    public String item;

    @ColumnInfo(name="obtained")
    public boolean obtained;
}

@Dao
public interface shoppingListsDAO {
    @Insert()
    void insert(shoppingListsTbl ... shoppingList);

    @Update
    void update(shoppingListsTbl... shoppingList);

    @Delete
    void delete(shoppingListsTbl shoppingList);

    @Query("SELECT DISTINCT list_names FROM shoppingListsTbl")
    List<String> shoppingListNames();

    // Lists intersection of items with a single shopping list
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT itemsTbl.category,itemsTbl.item_name "+
            "FROM itemsTbl INNER JOIN shoppingListsTbl ON shoppingListsTbl.item_name=itemsTbl.item_name "+
            "WHERE shoppingListsTbl.list_names LIKE :selectList")
    List<category_item_tuple> itemsByList(String selectList);

    // Lists items at the intersection of a shopping list and a category
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT shoppingListsTbl.item_name, shoppingListsTbl.obtained "+
            "FROM shoppingListsTbl INNER JOIN itemsTbl ON shoppingListsTbl.item_name=itemsTbl.item_name "+
            "WHERE shoppingListsTbl.list_names LIKE :selectList "+
            "AND itemsTbl.category LIKE :selectCategory "+
            "ORDER BY shoppingListsTbl.obtained")
    List<item_obtained_tuple> itemsByListAndCategory(String selectList, String selectCategory);
}
