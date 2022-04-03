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

import com.example.shoppinglist.adaptersandviews.catListAdapter;
import com.example.shoppinglist.adaptersandviews.catViewModel;
import com.example.shoppinglist.db.itemsTbl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class CategoriesActivity extends AppCompatActivity implements LifecycleOwner {
    // Only used for logging and debugging purposes
    final String TAG = "***categoriesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Declaring a viewModel
        final catViewModel viewModel=new ViewModelProvider(this).get(catViewModel.class);

        LiveData<List<itemsTbl>> catItemObject= viewModel.getAllItems();

        Context context=getApplicationContext();
        // Setting up item Callback
        @NonNull
        DiffUtil.ItemCallback<itemsTbl> diffCallback=new catListAdapter.catDiff();

        Log.d(TAG, "init recyclerView.");
        RecyclerView recyclerView = findViewById(R.id.category_recycler);

        Log.d(TAG, "init adapter");
        final catListAdapter adapter = new catListAdapter(new catListAdapter.catDiff(),context,catItemObject);

        Log.d(TAG, "set adapter.");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creating observer
        @NonNull
        final
        Observer<? super List<itemsTbl>> catObserver = (Observer<List<itemsTbl>>) itemsTbls -> {
            Log.d(TAG,"CatObserver");
            adapter.submitList((List<itemsTbl>) itemsTbls);
            //adapter.notifyDataSetChanged();
        };

        @NonNull
        Observer<? super java.util.List<itemsTbl>> catAndItemObserver= (Observer<List<itemsTbl>>) newItem -> {
            Log.d(TAG,"observer onChanged method called");
            adapter.submitList((List<itemsTbl>) newItem);
            //adapter.notifyDataSetChanged();
        };

        // setting up viewModel and observer
        //viewModel=new ViewModelProvider(this).get(catViewModel.class);
        viewModel.getAllItems().observe(this, catObserver);
        //viewModel.getItemsByCategory().observe(this, catAndItemObserver);

        Log.d(TAG, "onCreate: created.");

        // Connecting buttons in XML file to Button objects in java class
        final FloatingActionButton newListBtn = findViewById(R.id.new_category_btn);
        final Button returnBtn = findViewById(R.id.return_btn2);


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
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
                builder.setTitle("Enter the category name");
                Context context=CategoriesActivity.this;
                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                // Text boxes for input
                final EditText input1=new EditText(context);
                final EditText input2=new EditText(context);
                input1.setHint("Category name");
                input2.setHint("Item name");
                layout.addView(input1);
                layout.addView(input2);

                builder.setView(layout);

                // Adding buttons to submit or cancel data
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveData(input1.getText().toString(),input2.getText().toString(), viewModel, catItemObject);
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


    // Write method
    public void saveData(String cat, String item, catViewModel viewModel, LiveData<List<itemsTbl>> catItemObject) {
        Log.d(TAG,"saving data");
        itemsTbl newRecord=new itemsTbl(item,cat);
        viewModel.insert(newRecord);
        catItemObject=viewModel.getAllItems();
    }
}