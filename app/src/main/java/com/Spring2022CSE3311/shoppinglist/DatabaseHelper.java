package com.Spring2022CSE3311.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
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
                                COLUMN_CATEGORY_TEXT_COLOR          + " TEXT )";
        sqLiteDatabase.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE "                  + ITEM_TABLE  + "( " +
                                COLUMN_ITEM_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COLUMN_ITEM_NAME                + " TEXT,"    +
                                COLUMN_CATEGORY_ID              + " INTEGER," +
                                COLUMN_SHOPPINGLIST_ID          + " INTEGER," +
                               "FOREIGN KEY(" + COLUMN_CATEGORY_ID +     ") REFERENCES " + CATEGORY_TABLE     + "(" + COLUMN_CATEGORY_ID     +") ON DELETE CASCADE, " +
                               "FOREIGN KEY(" + COLUMN_SHOPPINGLIST_ID + ") REFERENCES " + SHOPPINGLIST_TABLE + "(" + COLUMN_SHOPPINGLIST_ID +") ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE "                          + SHOPPINGLIST_TABLE + "( " +
                                COLUMN_SHOPPINGLIST_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                COLUMN_SHOPPINGLIST_NAME                + " TEXT, " +
                                COLUMN_SHOPPINGLIST_DATE                + " TEXT)";
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
        db.close();
        return inserted != -1;
    }

    public boolean deleteOne(SList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(SHOPPINGLIST_TABLE, "COLUMN_SHOPPINGLIST_ID=?", new String[] {"" + list.getId()});
        db.close();
        return deleted != -1;
    }

    public boolean updateOne(SList old, SList update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SHOPPINGLIST_NAME, update.getListName());
        cv.put(COLUMN_SHOPPINGLIST_DATE, update.getDate());

        long replaced = db.update(SHOPPINGLIST_TABLE, cv, "COLUMN_SHOPPINGLIST_ID=?", new String[] {"" + old.getId()});
        db.close();
        return replaced != -1;
    }

    public boolean addOne(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, item.getItemName());
        cv.put(COLUMN_CATEGORY_ID, item.getItemCategory().getId());
        cv.put(COLUMN_SHOPPINGLIST_ID, item.getListID());

        long inserted = db.insert((ITEM_TABLE), null, cv);
        db.close();
        return inserted != -1;
    }

    public boolean deleteOne(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(ITEM_TABLE, "COLUMN_ITEM_ID=?", new String[] {"" + item.getId()});
        db.close();
        return deleted != -1;
    }

    public boolean updateOne(Item old, Item update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, update.getItemName());
        cv.put(COLUMN_CATEGORY_ID, update.getItemCategory().getId());
        cv.put(COLUMN_SHOPPINGLIST_ID, update.getListID());

        long replaced = db.update(ITEM_TABLE, cv, "COLUMN_ITEM_ID=?", new String[] {"" + old.getId()});
        db.close();
        return replaced != -1;
    }

    public boolean addOne(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY_NAME, category.getCategoryName());
        cv.put(COLUMN_CATEGORY_BACKGROUND_COLOR, category.getBackgroundColor());
        cv.put(COLUMN_CATEGORY_TEXT_COLOR, category.getTextColor());

        long inserted = db.insert(CATEGORY_TABLE, null, cv);
        db.close();
        return inserted != -1;
    }

    public boolean deleteOne(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(CATEGORY_TABLE, "COLUMN_CATEGORY_ID=?", new String[] {"" + category.getId()});
        db.close();
        return deleted != -1;
    }

    public boolean updateOne(Category old, Category update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY_NAME, update.getCategoryName());
        cv.put(COLUMN_CATEGORY_BACKGROUND_COLOR, update.getBackgroundColor());
        cv.put(COLUMN_CATEGORY_TEXT_COLOR, update.getTextColor());

        long replaced = db.update(CATEGORY_TABLE, cv, "COLUMN_CATEGORY_ID=?", new String[] {"" + old.getId()});
        db.close();
        return replaced != -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> getShoppingListItems(SList list) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> items = new ArrayList<>();

        String searchQuery =    "SELECT " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_CATEGORY_ID + ", " + COLUMN_CATEGORY_NAME + ", " +
                                 COLUMN_CATEGORY_BACKGROUND_COLOR + ", " + COLUMN_CATEGORY_TEXT_COLOR + " " +
                                "FROM " + SHOPPINGLIST_TABLE + " NATURAL JOIN " + ITEM_TABLE + " AS I NATURAL JOIN " + CATEGORY_TABLE + " " +
                                "WHERE I." + COLUMN_SHOPPINGLIST_ID + " = " + list.getId();

        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {
                    int    itemID    = cursor.getInt(0);
                    String itemName  = cursor.getString(1);
                    int    catId     = cursor.getInt(2);
                    String catName   = cursor.getString(3);
                    String backColor = cursor.getString(4);
                    String textColor = cursor.getString(5);

                    Category cat = new Category(catId, catName, backColor, textColor);
                    Item item = new Item(itemID, itemName, cat, list.getId());

                    items.add(item);
            } while(cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();

        Collections.sort(items, new Item.ItemComparator());

        return items;
    }

    public List<Category> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Category> categories = new ArrayList<>();

        String searchQuery =    "SELECT " + COLUMN_CATEGORY_ID + ", " + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_BACKGROUND_COLOR + ", " + COLUMN_CATEGORY_TEXT_COLOR + " " +
                "FROM " + CATEGORY_TABLE;

        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {

                int    catId     = cursor.getInt(0);
                String catName   = cursor.getString(1);
                String backColor = cursor.getString(2);
                String textColor = cursor.getString(3);

                Category cat = new Category(catId, catName, backColor, textColor);

                categories.add(cat);
            } while(cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();


        return categories;
    }

    public List<SList> getLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<SList> lists = new ArrayList<>();

        String searchQuery =    "SELECT " + COLUMN_SHOPPINGLIST_ID + ", " + COLUMN_SHOPPINGLIST_NAME + ", " + COLUMN_SHOPPINGLIST_DATE + " " +
                                "FROM " + SHOPPINGLIST_TABLE;

        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {

                int    listID     = cursor.getInt(0);
                String listName   = cursor.getString(1);
                String listDate   = cursor.getString(2);

                SList list = new SList(listID, listName, listDate);

                lists.add(list);
            } while(cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();


        return lists;
    }
}
