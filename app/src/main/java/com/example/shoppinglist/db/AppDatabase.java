package com.example.shoppinglist.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {itemsTbl.class, shoppingListsTbl.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public static String TAG="***AppDatabase";
    int log=Log.d(TAG,"Starting database");

    // One Data Access Object for each table
    public abstract itemsDAO itemsDao();
    public abstract shoppingListsDAO shoppingListDao();

    private static volatile AppDatabase db;

    // Room database queries must be run asynchronously
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Create an instance of the database
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

    // Create an instance of the database
    public static synchronized AppDatabase getInstance(Context context){
        if(db==null){
            db= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database").build();
        }
        return db;
    }

    // Attempt to use a Callback to add some data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase dbSQLite) {
            super.onCreate(dbSQLite);
            databaseWriteExecutor.execute(() -> {
                shoppingListsDAO dao = db.shoppingListDao();
                shoppingListsTbl SL = new shoppingListsTbl("groceries","onions",false);
                dao.insert(SL);
            });
        }
    };
}




