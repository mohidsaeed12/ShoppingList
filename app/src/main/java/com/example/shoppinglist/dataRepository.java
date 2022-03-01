package com.example.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;

public class dataRepository {
    private String selectList;
    private String selectCategory;
    private shoppingListsDAO localSLdao;
    private shoppingListsTbl shoppingListObject=new shoppingListsTbl();

    public String getSelectList(){return this.selectList;}
    public String getSelectCategory(){return this.selectCategory;}
    public shoppingListsTbl getShoppingListObject(){return this.shoppingListObject;}

    public void setSelectList(String selectedList){this.selectList=selectedList;}
    public void setSelectCategory(String selectedCategory){this.selectCategory=selectedCategory;}
    public void setShoppingListObject(String listName,String itemName, boolean obtained) {
        this.shoppingListObject=new shoppingListsTbl(listName,itemName,obtained);
    }

    LiveData<List<String>> localSLnames;
    LiveData<List<category_item_tuple>> localSLcatAndItem;
    LiveData<List<item_obtained_tuple>> localSLitemAndObtained;
    LiveData<List<shoppingListsTbl>> localSLrecord;
    LiveData<List<SLrecordTuple>> localSLrecordTuple;

    private itemsDAO localItemsDao;
    LiveData<List<String>> localCatNames;

    dataRepository(Application application){
        AppDatabase db=AppDatabase.getDB(application);

        localSLdao=db.shoppingListDao();
        localSLnames=localSLdao.shoppingListNames();
        localSLcatAndItem=localSLdao.itemsByList(getSelectList());
        localSLitemAndObtained=localSLdao.itemsByListAndCategory(getSelectList(), getSelectCategory());
        localSLrecord=localSLdao.shoppingListRecords();

        localItemsDao=db.itemsDao();
        localCatNames=localItemsDao.categoryNames();
    }

    void SLinsert(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.insert(shoppingListObject);
        });
    }

    void SLupdate(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.update(shoppingListObject);
        });
    }

    void SLdelete(shoppingListsTbl shoppingListObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localSLdao.delete(shoppingListObject);
        });
    }
}
