package com.example.shoppinglist.adaptersandviews;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shoppinglist.dataRepository;
import com.example.shoppinglist.datatuples.item_obtained_tuple;
import com.example.shoppinglist.db.shoppingListsTbl;

import java.util.List;

public class slViewModel extends AndroidViewModel {
    // Declare the dataRepository
    private dataRepository repo;

    // LiveData objects necessary for asynchronous queries
    private final LiveData<java.util.List<item_obtained_tuple>> SLitemsObtained;
    private final LiveData<java.util.List<shoppingListsTbl>> SLtblRecord;
    private List<String> SLnamesSync;
    private List<shoppingListsTbl> SLrecsSync;

    // Constructor
    public slViewModel(Application application) {
        super(application);
        repo=new dataRepository(application);
        SLitemsObtained = repo.localSLitemAndObtained;
        SLtblRecord=repo.localSLrecord;
        SLnamesSync=repo.localSLnamesSync;
        SLrecsSync=repo.localSLrecordSync;
    }

    // Read access methods
    LiveData<java.util.List<item_obtained_tuple>> getSLbyCat()      {return SLitemsObtained;}
    public LiveData<java.util.List<shoppingListsTbl>> getSLrecords(){return SLtblRecord; }
    public List<shoppingListsTbl> getSLrecsSync()                   {return SLrecsSync;}
    public List<String> getSLnamesSync()                            {return SLnamesSync;}

    // Write access methods
    public void insert(shoppingListsTbl slTable){repo.SLinsert(slTable);}
    public void update(shoppingListsTbl slTable){repo.SLupdate(slTable);}
    public void delete(shoppingListsTbl slTable){repo.SLdelete(slTable);}
}
