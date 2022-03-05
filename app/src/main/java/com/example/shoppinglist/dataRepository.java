package com.example.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;
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

    LiveData<List<String>> localSLnames;
    LiveData<List<itemsTbl>> localSLcatAndItem;
    LiveData<List<item_obtained_tuple>> localSLitemAndObtained;
    LiveData<List<shoppingListsTbl>> localSLrecord;
    LiveData<List<SLrecordTuple>> localSLrecordTuple;


    private itemsDAO localItemsDao;
    LiveData<List<String>> localCatNames;
    LiveData<List<itemsTbl>> localCatAllItems;
    LiveData<List<itemsTbl>> localCatItemsByCat;


    dataRepository(Application application){
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

    void catInsert(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.insert(itemsTblObject);
        });
    }

    void catUpdate(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.update(itemsTblObject);
        });
    }

    void catDelete(itemsTbl itemsTblObject){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            localItemsDao.delete(itemsTblObject);
        });
    }
}
