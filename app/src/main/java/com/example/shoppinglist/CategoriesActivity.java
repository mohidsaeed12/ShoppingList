package com.example.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "categoriesActivity";

    public static final String SHARED_PREFS_CATEGORIES = "sharedprefscategories";
    public static final String CATEGORIES = "categories";

    // Vars
    ArrayList<List> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        loadData();
        Log.d(TAG, "onCreate: created.");


        final FloatingActionButton newListBtn = findViewById(R.id.new_category_btn);
        final Button returnBtn = findViewById(R.id.return_btn2);
        initRecyclerView();

        newListBtn.setOnClickListener(this::onClick);

        returnBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        RecyclerView recyclerView = findViewById(R.id.category_recycler);
        CustomAdapter adapter = new CustomAdapter(this, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CATEGORIES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        editor.putString(CATEGORIES, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CATEGORIES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CATEGORIES, null);
        Type type = new TypeToken<ArrayList<List>>() {}.getType();
        categories = gson.fromJson(json, type);

        if(categories == null) {
            categories = new ArrayList<>();
        }
    }

    private void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
        builder.setTitle("Enter the name for the new category");

        final EditText input = new EditText(CategoriesActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            private DialogInterface dialogInterface;
            private int i;

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                this.dialogInterface = dialogInterface;
                this.i = i;
                categories.add(new List(input.getText().toString()));
                saveData();
            }
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }
}