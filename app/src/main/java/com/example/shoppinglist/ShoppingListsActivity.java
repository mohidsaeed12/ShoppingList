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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.LinearLayout.VERTICAL;

public class ShoppingListsActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingListsActivity";

    private slViewModel viewModel;
    @NonNull DiffUtil.ItemCallback diffCallback;
    @NonNull
    Observer<? super java.util.List<shoppingListsTbl>> shoppingListObserver=new Observer<java.util.List<shoppingListsTbl>>() {
        @Override
        public void onChanged(java.util.List<shoppingListsTbl> shoppingListsTbls) {
            shoppingListObserver.notifyAll();
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
                Context context=ShoppingListsActivity.this;
                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                final EditText input1=new EditText(context);
                final EditText input2=new EditText(context);
                input1.setHint("Shopping list name");
                input2.setHint("Item name");
                layout.addView(input1);
                layout.addView(input2);

                builder.setView(layout);
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
                //setContentView(R.layout.activity_shopping_lists);
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        //setContentView(R.layout.activity_shopping_lists);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        RecyclerView recyclerView=findViewById(R.id.list_recycler);
        final SLlistAdapter adapter=new SLlistAdapter(new SLlistAdapter.SLdiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void saveData(String name) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,"",false);
        viewModel.insert(newRecord);
    }

    public void saveData(String name, String item) {
        shoppingListsTbl newRecord=new shoppingListsTbl(name,item,false);
        viewModel.insert(newRecord);
    }
}