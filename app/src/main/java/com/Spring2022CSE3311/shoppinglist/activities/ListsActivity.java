package com.Spring2022CSE3311.shoppinglist.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.Category;
import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.Item;
import com.Spring2022CSE3311.shoppinglist.R;
import com.Spring2022CSE3311.shoppinglist.SList;
import com.Spring2022CSE3311.shoppinglist.adaptersandviews.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class ListsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static RecyclerView recyclerView;
    private static RecyclerView.Adapter itemAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static AdapterView.OnItemSelectedListener listener;

    private Button btn_return;
    private FloatingActionButton btn_newItem;

    private static DatabaseHelper db;

    public static List<Category> categories;
    public static String[] categoryNames;
    private int position;
    private static SList list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        getSupportActionBar().setTitle("Items Screen");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            list = (SList) extras.getSerializable("List");
        }

        listener = this;

        db = new DatabaseHelper(this);

        categories = db.getCategories();
        categoryNames = new String[categories.size()];
        for(int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).getCategoryName();
        }

        recyclerView = findViewById(R.id.rv_items);
        setAdapter(recyclerView, this);

        btn_return = findViewById(R.id.btn_return3);
        btn_newItem = findViewById(R.id.btn_newItem);

        btn_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = ListsActivity.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Create a new item");
                builder.setCancelable(false);

                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                // Text boxes for input
                final EditText input1 = new EditText(context);
                input1.setHint("Item Name");
                layout.addView(input1);

                final Spinner input2 = new Spinner(context);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, categoryNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input2.setAdapter(arrayAdapter);
                input2.setOnItemSelectedListener(listener);
                layout.addView(input2);

                builder.setView(layout);

                // Adding buttons to submit or cancel data
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input1.getText().toString().equals(""))
                        {
                            Item newItem = new Item(input1.getText().toString(), categories.get(position), list.getId());
                            db.addOne(newItem);
                            itemAdapter.notifyDataSetChanged();
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
                Intent items2lists = new Intent(ListsActivity.this, ShoppingListsActivity.class);
                startActivity(items2lists);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        position = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setAdapter(RecyclerView recyclerView, Context context) {
        layoutManager = new LinearLayoutManager(context);
        itemAdapter = new ItemAdapter(db.getShoppingListItems(list),context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setHasFixedSize(true);
    }
}
