package com.example.shoppinglist;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {itemsTbl.class,shoppingListsTbl.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract itemsDAO itemsDao();
    public abstract shoppingListsDAO shoppingListDao();
}




