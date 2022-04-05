package com.Spring2022CSE3311.shoppinglist.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.Category;
import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.R;
import com.Spring2022CSE3311.shoppinglist.SList;
import com.Spring2022CSE3311.shoppinglist.adaptersandviews.CatAdapter;
import com.Spring2022CSE3311.shoppinglist.adaptersandviews.SLAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.LinearLayout.VERTICAL;
import static androidx.recyclerview.widget.DiffUtil.ItemCallback;

public class ShoppingListsActivity extends AppCompatActivity {
    // Only used for logging and debugging purposes
    private static final String TAG = "ShoppingListsActivity";

    public static RecyclerView recyclerView;
    private static RecyclerView.Adapter slAdapter;
    private static RecyclerView.LayoutManager layoutManager;

    private Button btn_return;
    private FloatingActionButton btn_newList;

    private static DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        getSupportActionBar().setTitle("Shopping List Screen");

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_shoppingLists);
        setAdapter(recyclerView, this);

        btn_return = findViewById(R.id.btn_return2);
        btn_newList = findViewById(R.id.btn_createList);

        btn_newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = ShoppingListsActivity.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Create a new list");
                builder.setCancelable(false);

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                // Text boxes for input
                final EditText input1 = new EditText(context);
                input1.setHint("List Name");
                layout.addView(input1);

                builder.setView(layout);

                // Adding buttons to submit or cancel data
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input1.getText().toString().equals(""))
                        {
                            SList newList = new SList(input1.getText().toString());
                            db.addOne(newList);
                            slAdapter.notifyDataSetChanged();
                            setAdapter(recyclerView, context);
                        }
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

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppinglists2main = new Intent(ShoppingListsActivity.this, MainActivity.class);
                startActivity(shoppinglists2main);
            }
        });
    }

    public static void setAdapter(RecyclerView recyclerView, Context context) {
        layoutManager = new LinearLayoutManager(context);
        slAdapter = new SLAdapter(db.getLists(), context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(slAdapter);
        recyclerView.setHasFixedSize(true);
    }
}