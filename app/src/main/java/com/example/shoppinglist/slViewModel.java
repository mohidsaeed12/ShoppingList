package com.example.shoppinglist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class slViewModel extends AndroidViewModel {
    private dataRepository repo;
    private final LiveData<java.util.List<item_obtained_tuple>> SLitemsObtained;
    private final LiveData<java.util.List<shoppingListsTbl>> SLtblRecord;
    public slViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        SLitemsObtained = repo.localSLitemAndObtained;
        SLtblRecord=repo.localSLrecord;
    }

    LiveData<java.util.List<item_obtained_tuple>> getSLbyCat(){
        return SLitemsObtained;
    }
    LiveData<java.util.List<shoppingListsTbl>> getSLrecords(){
        return SLtblRecord;
    }
    public void insert(shoppingListsTbl slTable){repo.SLinsert(slTable);}
    public void update(shoppingListsTbl slTable){repo.SLupdate(slTable);}
    public void delete(shoppingListsTbl slTable){repo.SLdelete(slTable);}
}
