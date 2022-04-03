package com.example.shoppinglist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shoppinglist.datatuples.SLrecordTuple;
import com.example.shoppinglist.datatuples.item_obtained_tuple;
import com.example.shoppinglist.db.AppDatabase;
import com.example.shoppinglist.db.itemsDAO;
import com.example.shoppinglist.db.itemsTbl;
import com.example.shoppinglist.db.shoppingListsDAO;
import com.example.shoppinglist.db.shoppingListsTbl;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class dataRepository {
    // Declaring data access objects
    private shoppingListsDAO localSLdao;
    private itemsDAO localItemsDao;

    private String selectList;
    private String selectCategory;

    // These each represent a single row in one of the tables
    private shoppingListsTbl shoppingListObject=new shoppingListsTbl();
    private itemsTbl itemsTblObject=new itemsTbl();

    // Getter methods
    public String getSelectList(){return this.selectList;}
    public String getSelectCategory(){return this.selectCategory;}
    public shoppingListsTbl getShoppingListObject(){return this.shoppingListObject;}
    public itemsTbl getItemsTblObject(){return this.itemsTblObject;}

    // Setter methods
    public void setSelectList(String selectedList){this.selectList=selectedList;}
    public void setSelectCategory(String selectedCategory){this.selectCategory=selectedCategory;}
    public void setShoppingListObject(String listName,String itemName, boolean obtained) {
        this.shoppingListObject=new shoppingListsTbl(listName,itemName,obtained);
    }
    public void setItemsTblObject(String item, String category){
        this.itemsTblObject=new itemsTbl(item, category);
    }

    // LiveData to allow Room to keep UI up to date on the information stored in the DB
    public LiveData<List<String>> localSLnames;
    public LiveData<List<itemsTbl>> localSLcatAndItem;
    public LiveData<List<item_obtained_tuple>> localSLitemAndObtained;
    public LiveData<List<shoppingListsTbl>> localSLrecord;
    public LiveData<List<SLrecordTuple>> localSLrecordTuple;

    public LiveData<List<String>> localCatNames;
    public LiveData<List<itemsTbl>> localCatAllItems;
    public LiveData<List<itemsTbl>> localCatItemsByCat;

    public List<itemsTbl> localCatAllItemsSync;


    public dataRepository(Application application){
        // Setting up the connection between the repository and the database
        AppDatabase db=AppDatabase.getDB(application);
        localSLdao=db.shoppingListDao();
        localItemsDao=db.itemsDao();

        // Setting up read access to to the database
        // Used by ViewModels
        localSLnames=localSLdao.shoppingListNames();
        localSLcatAndItem=localSLdao.itemsByList(getSelectList());
        localSLitemAndObtained=localSLdao.itemsByListAndCategory(getSelectList(), getSelectCategory());
        localSLrecord=localSLdao.shoppingListRecords();

        localCatNames=localItemsDao.categoryNames();
        localCatAllItems=localItemsDao.showAllItems();
        localCatItemsByCat=localItemsDao.itemsByCategory(getSelectCategory());

        localCatAllItemsSync=getItemsAsync();
    }

    public List<itemsTbl> getItemsAsync(){
        List<itemsTbl> temp = null;
        try {
            temp= new getItemsAsynchronously(localItemsDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private static class getItemsAsynchronously extends AsyncTask<Void, Void, List<itemsTbl>>{

        private itemsDAO itemsDao;

        getItemsAsynchronously(itemsDAO itemsDao){
            this.itemsDao=itemsDao;
        }

        @Override
        protected List<itemsTbl> doInBackground(Void... voids) {
            return itemsDao.workaroundShowAllItems();
        }
    }

    /*private static class getItemsAsynchronously extends AsyncTask<itemsTbl, Void, itemsTbl[]> {

        private itemsDAO itemsDao;

        getItemsAsynchronously(itemsDAO itemsDao){
            this.itemsDao=itemsDao;
        }

        @Override
        protected itemsTbl[] doInBackground(itemsTbl... itemsTbls) {
            itemsDao.workaroundShowAllItems();
            return itemsTbls;
        }
    }*/

    // Setting up write access to the database
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
