package com.example.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.adaptersandviews.SLlistAdapter;
import com.example.shoppinglist.adaptersandviews.slViewModel;
import com.example.shoppinglist.db.shoppingListsTbl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class ShoppingListsActivity extends AppCompatActivity implements LifecycleOwner {
    // Only used for logging and debugging purposes
    private static final String TAG = "*ShoppingListsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        final slViewModel viewModel=new ViewModelProvider(this).get(slViewModel.class);

        LiveData<List<shoppingListsTbl>> slRec= viewModel.getSLrecords();

        Context context=getApplicationContext();

        @NonNull
        DiffUtil.ItemCallback<shoppingListsTbl> diffCallback=new SLlistAdapter.SLdiff();

        RecyclerView recyclerView = findViewById(R.id.list_recycler);

        final SLlistAdapter adapter = new SLlistAdapter(new SLlistAdapter.SLdiff(),context,slRec);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creating observer
        @NonNull
        final
        Observer<? super List<shoppingListsTbl>> slObserver = (Observer<List<shoppingListsTbl>>) slTbls -> {
            Log.d(TAG,"CatObserver");
            adapter.submitList((List<shoppingListsTbl>) slTbls);
            //adapter.notifyDataSetChanged();
        };

        // setting up viewModel and observer
        viewModel.getSLrecords().observe(this, slObserver);

        Log.d(TAG, "onCreate: created.");

        // Connecting buttons in XML file to Button objects in java class
        final FloatingActionButton newListBtn = findViewById(R.id.new_list_btn);
        final Button returnBtn = findViewById(R.id.return_btn);

        // Setting up actions for buttons.
        //To do: consolidate into 1 OnClickListener as in the main activity
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        newListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Beginning the design of a dialog box to prompt user for data
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListsActivity.this);
                builder.setTitle("Enter the name for your list");
                Context context=ShoppingListsActivity.this;
                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                // Text boxes for input
                final EditText input1=new EditText(context);
                final EditText input2=new EditText(context);
                input1.setHint("Shopping list name");
                input2.setHint("Item name");
                layout.addView(input1);
                layout.addView(input2);

                builder.setView(layout);

                // Adding buttons to submit or cancel data
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveData(input1.getText().toString(),input2.getText().toString(),false,viewModel,slRec);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });
    }


    // Write methods
    public void saveData(String name, String item, boolean isObtained, slViewModel viewModel, LiveData<List<shoppingListsTbl>> slRec) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,item,isObtained);
        viewModel.insert(newRecord);
        slRec=viewModel.getSLrecords();
    }

}