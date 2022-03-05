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

import com.example.shoppinglist.adaptersandviews.catListAdapter;
import com.example.shoppinglist.adaptersandviews.catViewModel;
import com.example.shoppinglist.db.itemsTbl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.LinearLayout.VERTICAL;

public class CategoriesActivity extends AppCompatActivity {


    private static final String TAG = "categoriesActivity";

    private catViewModel viewModel;
    @NonNull
    DiffUtil.ItemCallback<itemsTbl> diffCallback=new catListAdapter.catDiff();
    @NonNull
    Observer<? super java.util.List<itemsTbl>> catObserver=new Observer<java.util.List<itemsTbl>>(){
        @Override
        public void onChanged(java.util.List<itemsTbl> itemsTbls){
            catObserver.notifyAll();
        }
    };

    catListAdapter adapter=new catListAdapter(diffCallback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        viewModel=new ViewModelProvider(this).get(catViewModel.class);
        viewModel.getAllItems().observe(this, catObserver->{adapter.submitList(catObserver);});


        Log.d(TAG, "onCreate: created.");


        final FloatingActionButton newListBtn = findViewById(R.id.new_category_btn);
        final Button returnBtn = findViewById(R.id.return_btn2);
        initRecyclerView();

        newListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
                builder.setTitle("Enter the category name");
                Context context=CategoriesActivity.this;
                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                final EditText input1=new EditText(context);
                final EditText input2=new EditText(context);
                input1.setHint("Category name");
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

        returnBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        RecyclerView recyclerView = findViewById(R.id.category_recycler);
        final catListAdapter adapter = new catListAdapter(new catListAdapter.catDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void saveData(String item, String cat) {
        itemsTbl newRecord=new itemsTbl(item,cat);
        viewModel.insert(newRecord);
    }


}