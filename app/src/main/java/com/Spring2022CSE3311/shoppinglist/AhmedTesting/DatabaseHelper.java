package com.Spring2022CSE3311.shoppinglist.AhmedTesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SHOPPINGLIST_TABLE = "SHOPPINGLIST_TABLE";
    public static final String COLUMN_SHOPPINGLIST_ID = "COLUMN_SHOPPINGLIST_ID";
    public static final String COLUMN_SHOPPINGLIST_NAME = "COLUMN_SHOPPINGLIST_NAME";
    public static final String COLUMN_SHOPPINGLIST_DATE = "COLUMN_SHOPPINGLIST_DATE";

    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COLUMN_ITEM_ID = "COLUMN_ITEM_ID";
    public static final String COLUMN_ITEM_NAME = "COLUMN_ITEM_NAME";

    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String COLUMN_CATEGORY_ID = "COLUMN_CATEGORY_ID";
    public static final String COLUMN_CATEGORY_NAME = "COLUMN_CATEGORY_NAME";
    public static final String COLUMN_CATEGORY_BACKGROUND_COLOR = "COLUMN_CATEGORY_BACKGROUND_COLOR";
    public static final String COLUMN_CATEGORY_TEXT_COLOR = "COLUMN_CATEGORY_TEXT_COLOR";

    public static final String COMBINED_TABLE = "COMBINED_TABLE";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "ShoppingList.db", null, 1);
    }


    // This function is called the first time the database is created
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "";

        // Creates the respective tables by creating a command string then executing it
        createTableStatement = "CREATE TABLE "                      + CATEGORY_TABLE + "( " +
                                COLUMN_CATEGORY_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COLUMN_CATEGORY_NAME                + " TEXT, " +
                                COLUMN_CATEGORY_BACKGROUND_COLOR    + " TEXT, " +
                                COLUMN_CATEGORY_TEXT_COLOR          + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE "                  + ITEM_TABLE + "( " +
                                COLUMN_ITEM_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COLUMN_ITEM_NAME                + " TEXT," +
                                COLUMN_CATEGORY_ID              + " INTEGER," +
                               "FOREIGN KEY("+COLUMN_CATEGORY_ID+") REFERENCES " + CATEGORY_TABLE + "("+COLUMN_CATEGORY_ID+"))";
        sqLiteDatabase.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE "                          + SHOPPINGLIST_TABLE + "( " +
                                COLUMN_SHOPPINGLIST_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                COLUMN_SHOPPINGLIST_NAME                + " TEXT, " +
                                COLUMN_SHOPPINGLIST_DATE                + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE "                      + COMBINED_TABLE + "( " +
                                COLUMN_SHOPPINGLIST_ID              + " INTEGER," +
                                COLUMN_ITEM_ID                      + " INTEGER," +
                               "FOREIGN KEY("+COLUMN_SHOPPINGLIST_ID+") REFERENCES " + SHOPPINGLIST_TABLE + "("+COLUMN_SHOPPINGLIST_ID+")," +
                               "FOREIGN KEY("+COLUMN_ITEM_ID+        ") REFERENCES " + ITEM_TABLE + "("+COLUMN_ITEM_ID+")," +
                               "PRIMARY KEY("+COLUMN_SHOPPINGLIST_ID+", "+COLUMN_ITEM_ID+"))";
        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(SList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SHOPPINGLIST_NAME, list.getListName());
        cv.put(COLUMN_SHOPPINGLIST_DATE, list.getDate());

        long inserted = db.insert(SHOPPINGLIST_TABLE, null, cv);
        return inserted != -1;
    }

    public boolean addOne(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, item.getItemName());
        cv.put(COLUMN_CATEGORY_NAME, item.getItemCategory().getId());

        long inserted = db.insert(SHOPPINGLIST_TABLE, null, cv);
        return inserted != -1;
    }

    public boolean addOne(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY_NAME, category.getCategoryName());
        cv.put(COLUMN_CATEGORY_BACKGROUND_COLOR, category.getBackgroundColor());
        cv.put(COLUMN_CATEGORY_TEXT_COLOR, category.getTextColor());

        long inserted = db.insert(SHOPPINGLIST_TABLE, null, cv);
        return inserted != -1;
    }

    // IN PROGRESS
    public List<Item> getShoppingList(int listID){
        List<Item> items = new ArrayList<>();


        return items;
    }
}
