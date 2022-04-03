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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.adaptersandviews.SLlistAdapter;
import com.example.shoppinglist.adaptersandviews.slViewModel;
import com.example.shoppinglist.db.shoppingListsTbl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.LinearLayout.VERTICAL;
import static androidx.recyclerview.widget.DiffUtil.ItemCallback;

public class ShoppingListsActivity extends AppCompatActivity {
    // Only used for logging and debugging purposes
    private static final String TAG = "*ShoppingListsActivity";

    // Declaring a viewModel
    private slViewModel viewModel;

    // Setting up item Callback
    @NonNull
    ItemCallback<shoppingListsTbl> diffCallback= new SLlistAdapter.SLdiff();

    // Creating observer
    @NonNull
    Observer<? super java.util.List<shoppingListsTbl>> shoppingListObserver=new Observer<java.util.List<shoppingListsTbl>>() {
        @Override
        public void onChanged(java.util.List<shoppingListsTbl> shoppingListsTbls) {
            shoppingListObserver.notifyAll();
        }
    };

    // Creating adapter
    SLlistAdapter adapter=new SLlistAdapter(diffCallback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        // setting up viewModel and observer
        viewModel=new ViewModelProvider(this).get(slViewModel.class);
        viewModel.getSLrecords().observe(this, shoppingListObserver->{adapter.submitList(shoppingListObserver);});

        Log.d(TAG, "onCreate: created.");

        // Connecting buttons in XML file to Button objects in java class
        final FloatingActionButton newListBtn = findViewById(R.id.new_list_btn);
        final Button returnBtn = findViewById(R.id.return_btn);
        initRecyclerView();

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
                        saveData(input1.getText().toString(),input2.getText().toString());
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

    // Setting up the recyclerView
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        RecyclerView recyclerView=findViewById(R.id.list_recycler);
        final SLlistAdapter adapter=new SLlistAdapter(new SLlistAdapter.SLdiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Write methods
    public void saveData(String name) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,"",false);
        viewModel.insert(newRecord);
    }

    public void saveData(String name, String item) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,item,false);
        viewModel.insert(newRecord);
    }
}