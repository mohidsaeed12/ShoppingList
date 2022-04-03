package com.example.shoppinglist.adaptersandviews;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shoppinglist.dataRepository;
import com.example.shoppinglist.db.itemsTbl;

import java.util.List;

public class catViewModel extends AndroidViewModel {
    private static final String TAG = "***catViewModel: ";
    // Declare the dataRepository
    private dataRepository repo;

    // LiveData objects necessary for asynchronous queries
    private final LiveData<java.util.List<String>> catNames;
    private final LiveData<java.util.List<itemsTbl>> catAllItems;
    private final LiveData<java.util.List<itemsTbl>> catItemsByCat;
    private List<itemsTbl> catAllItemsSync;

    private LiveData<java.util.List<itemsTbl>> temp;

    // Constructor
    public catViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        catNames = repo.localCatNames;
        catAllItems=repo.localCatAllItems;
        catItemsByCat=repo.localCatItemsByCat;
        catAllItemsSync=repo.localCatAllItemsSync;
        Log.d(TAG, "constructor");
    }


    // Read access methods
    public LiveData<java.util.List<String>> getCategoryNames(){return catNames;}

    public LiveData<java.util.List<itemsTbl>> getAllItems(){
        Log.d(TAG, "getAllItems");
        temp=catAllItems;
        return temp;
    }

    public java.util.List<itemsTbl> getAllItemsSync(){
        Log.d(TAG, "getAllItems");
        List<itemsTbl> temp=catAllItemsSync;
        return catAllItemsSync;
    }


    public LiveData<java.util.List<itemsTbl>> getItemsByCategory(){return catItemsByCat;}

    // Write access methods
    public void insert(itemsTbl item){repo.catInsert(item);}
    public void update(itemsTbl item){repo.catUpdate(item);}
    public void delete(itemsTbl item){repo.catDelete(item);}
}
