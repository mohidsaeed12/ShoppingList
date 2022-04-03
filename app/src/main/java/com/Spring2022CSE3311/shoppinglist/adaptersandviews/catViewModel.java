package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Spring2022CSE3311.shoppinglist.dataRepository;
import com.Spring2022CSE3311.shoppinglist.db.itemsTbl;

public class catViewModel extends AndroidViewModel {
    // Declare the dataRepository
    private dataRepository repo;

    // LiveData objects necessary for asynchronous queries
    private final LiveData<java.util.List<String>> catNames;
    private final LiveData<java.util.List<itemsTbl>> catAllItems;
    private final LiveData<java.util.List<itemsTbl>> catItemsByCat;

    // Constructor
    public catViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        catNames = repo.localCatNames;
        catAllItems=repo.localCatAllItems;
        catItemsByCat=repo.localCatItemsByCat;
    }

    // Read access methods
    public LiveData<java.util.List<String>> getCategoryNames(){return catNames;}
    public LiveData<java.util.List<itemsTbl>> getAllItems(){return catAllItems;}
    public LiveData<java.util.List<itemsTbl>> getItemsByCategory(){return catItemsByCat;}

    // Write access methods
    public void insert(itemsTbl item){repo.catInsert(item);}
    public void update(itemsTbl item){repo.catUpdate(item);}
    public void delete(itemsTbl item){repo.catDelete(item);}
}
