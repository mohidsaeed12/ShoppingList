package com.example.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoppingListsActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingListsActivity";

    private slViewModel viewModel;
    @NonNull DiffUtil.ItemCallback diffCallback;
    @NonNull
    Observer<? super java.util.List<shoppingListsTbl>> shoppingListObserver=new Observer<java.util.List<shoppingListsTbl>>() {
        @Override
        public void onChanged(java.util.List<shoppingListsTbl> shoppingListsTbls) {
            shoppingListObserver.notify();
        }
    };
    SLlistAdapter adapter=new SLlistAdapter(diffCallback);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        viewModel=new ViewModelProvider(this).get(slViewModel.class);
        viewModel.getSLrecords().observe(this, shoppingListObserver->{adapter.submitList(shoppingListObserver);});


        Log.d(TAG, "onCreate: created.");

        final FloatingActionButton newListBtn = findViewById(R.id.new_list_btn);
        final Button returnBtn = findViewById(R.id.return_btn);
        initRecyclerView();

        newListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListsActivity.this);
                builder.setTitle("Enter the name for your list");

                final EditText input = new EditText(ShoppingListsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //lists.add(new List<String>(input.getText().toString()));
                        saveData(input.getText().toString());
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

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveData();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        RecyclerView recyclerView=findViewById(R.id.list_recycler);
        final SLlistAdapter adapter=new SLlistAdapter(new SLlistAdapter.SLdiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveData(String name) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,"",false);
        viewModel.insert(newRecord);
    }


}