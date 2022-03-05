package com.example.shoppinglist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class catViewModel extends AndroidViewModel {
    private dataRepository repo;

    private final LiveData<java.util.List<String>> catNames;
    private final LiveData<java.util.List<itemsTbl>> catAllItems;
    private final LiveData<java.util.List<itemsTbl>> catItemsByCat;

    public catViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        catNames = repo.localCatNames;
        catAllItems=repo.localCatAllItems;
        catItemsByCat=repo.localCatItemsByCat;
    }
    LiveData<java.util.List<String>> getCategoryNames(){return catNames;}
    LiveData<java.util.List<itemsTbl>> getAllItems(){return catAllItems;}
    LiveData<java.util.List<itemsTbl>> getItemsByCategory(){return catItemsByCat;}

    public void insert(itemsTbl item){repo.catInsert(item);}
    public void update(itemsTbl item){repo.catUpdate(item);}
    public void delete(itemsTbl item){repo.catDelete(item);}
}
