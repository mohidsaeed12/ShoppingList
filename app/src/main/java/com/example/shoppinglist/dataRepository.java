package com.example.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.db.*;
import com.example.shoppinglist.datatuples.*;


import java.util.List;

public class dataRepository {
    private String selectList;
    private String selectCategory;
    private shoppingListsDAO localSLdao;
    private shoppingListsTbl shoppingListObject=new shoppingListsTbl();
    private itemsTbl itemsTblObject=new itemsTbl();

    public String getSelectList(){return this.selectList;}
    public String getSelectCategory(){return this.selectCategory;}
    public shoppingListsTbl getShoppingListObject(){return this.shoppingListObject;}
    public itemsTbl getItemsTblObject(){return this.itemsTblObject;}

    public void setSelectList(String selectedList){this.selectList=selectedList;}
    public void setSelectCategory(String selectedCategory){this.selectCategory=selectedCategory;}
    public void setShoppingListObject(String listName,String itemName, boolean obtained) {
        this.shoppingListObject=new shoppingListsTbl(listName,itemName,obtained);
    }
    public void setItemsTblObject(String item, String category){
        this.itemsTblObject=new itemsTbl(item, category);
    }

    public LiveData<List<String>> localSLnames;
    public LiveData<List<itemsTbl>> localSLcatAndItem;
    public LiveData<List<item_obtained_tuple>> localSLitemAndObtained;
    public LiveData<List<shoppingListsTbl>> localSLrecord;
    public LiveData<List<SLrecordTuple>> localSLrecordTuple;


    private itemsDAO localItemsDao;
    public LiveData<List<String>> localCatNames;
    public LiveData<List<itemsTbl>> localCatAllItems;
    public LiveData<List<itemsTbl>> localCatItemsByCat;


    public dataRepository(Application application){
        AppDatabase db=AppDatabase.getDB(application);

        localSLdao=db.shoppingListDao();
        localSLnames=localSLdao.shoppingListNames();
        localSLcatAndItem=localSLdao.itemsByList(getSelectList());
        localSLitemAndObtained=localSLdao.itemsByListAndCategory(getSelectList(), getSelectCategory());
        localSLrecord=localSLdao.shoppingListRecords();

        localItemsDao=db.itemsDao();
        localCatNames=localItemsDao.categoryNames();
        localCatAllItems=localItemsDao.showAllItems();
        localCatItemsByCat=localItemsDao.itemsByCategory(selectCategory);
    }

    public void SLinsert(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.insert(shoppingListObject);
        });
    }

    public void SLupdate(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.update(shoppingListObject);
        });
    }

    public void SLdelete(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.delete(shoppingListObject);
        });
    }

    public void catInsert(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.insert(itemsTblObject);
        });
    }

    public void catUpdate(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.update(itemsTblObject);
        });
    }

    public void catDelete(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.delete(itemsTblObject);
        });
    }
}
