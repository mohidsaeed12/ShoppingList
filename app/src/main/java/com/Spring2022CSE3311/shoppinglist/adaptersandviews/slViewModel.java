package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Spring2022CSE3311.shoppinglist.dataRepository;
import com.Spring2022CSE3311.shoppinglist.datatuples.item_obtained_tuple;
import com.Spring2022CSE3311.shoppinglist.db.shoppingListsTbl;

public class slViewModel extends AndroidViewModel {
    // Declare the dataRepository
    private dataRepository repo;

    // LiveData objects necessary for asynchronous queries
    private final LiveData<java.util.List<item_obtained_tuple>> SLitemsObtained;
    private final LiveData<java.util.List<shoppingListsTbl>> SLtblRecord;

    // Constructor
    public slViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        SLitemsObtained = repo.localSLitemAndObtained;
        SLtblRecord=repo.localSLrecord;
    }

    // Read access methods
    LiveData<java.util.List<item_obtained_tuple>> getSLbyCat(){
        return SLitemsObtained;
    }
    public LiveData<java.util.List<shoppingListsTbl>> getSLrecords(){ return SLtblRecord; }

    // Write access methods
    public void insert(shoppingListsTbl slTable){repo.SLinsert(slTable);}
    public void update(shoppingListsTbl slTable){repo.SLupdate(slTable);}
    public void delete(shoppingListsTbl slTable){repo.SLdelete(slTable);}
}
