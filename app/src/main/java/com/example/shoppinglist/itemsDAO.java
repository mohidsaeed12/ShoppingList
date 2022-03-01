package com.example.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface itemsDAO {
    @Insert()
    void insert(itemsTbl ... items);

    @Update
    void update(itemsTbl... items);

    @Delete
    void delete(itemsTbl item);

    @Query("SELECT DISTINCT category FROM itemsTbl")
    LiveData<List<String>> categoryNames();
}
