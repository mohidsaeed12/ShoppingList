package com.example.shoppinglist.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {itemsTbl.class, shoppingListsTbl.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract itemsDAO itemsDao();
    public abstract shoppingListsDAO shoppingListDao();

    private static volatile AppDatabase db;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDB(final Context context){
        if(db==null){
            synchronized (AppDatabase.class){
                if(db==null){
                    db=Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database").build();
                }
            }
        }
        return db;
    }

    public static synchronized AppDatabase getInstance(Context context){
        if(db==null){
            //db= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
            db= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database").build();
        }
        return db;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase dbSQLite) {
            super.onCreate(dbSQLite);
            databaseWriteExecutor.execute(() -> {
                shoppingListsDAO dao = db.shoppingListDao();
                //dao.delete();

                shoppingListsTbl SL = new shoppingListsTbl("groceries","onions",false);
                dao.insert(SL);
            });
        }
    };
}




