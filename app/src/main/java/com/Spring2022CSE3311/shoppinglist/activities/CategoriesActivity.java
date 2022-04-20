package com.Spring2022CSE3311.shoppinglist.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.Category;
import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.R;
import com.Spring2022CSE3311.shoppinglist.adaptersandviews.CatAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String[] resources;

    public static RecyclerView recyclerView;
    private static RecyclerView.Adapter catAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static AdapterView.OnItemSelectedListener listener;

    private Button btn_return;
    private FloatingActionButton btn_newCategory;

    private static DatabaseHelper db;

    private String colorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Category Screen");

        listener = this;
        resources = getResources().getStringArray(R.array.color_values);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_categories);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        setAdapter(recyclerView, this);

        btn_return = findViewById(R.id.btn_return);
        btn_newCategory = findViewById(R.id.btn_newCategory);

        btn_newCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = CategoriesActivity.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Create a new category");
                builder.setCancelable(false);

                LinearLayout layout=new LinearLayout(context);
                layout.setOrientation(VERTICAL);

                // Text boxes for input
                final EditText input1 = new EditText(context);
                input1.setHint("Category Name");
                layout.addView(input1);

                /*final EditText input2 = new EditText(context);
                input2.setHint("Place in sort order");
                layout.addView(input2);*/

                final Spinner input3 = new Spinner(context);
                ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.color_names, android.R.layout.simple_spinner_item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                input3.setAdapter(arrayAdapter);
                input3.setOnItemSelectedListener(listener);
                layout.addView(input3);

                builder.setView(layout);

                // Adding buttons to submit or cancel data
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input1.getText().toString().equals(""))
                        {
                            String colors[];
                            //int rank;
                            //rank=(input2.getText().toString().equals(""))? 1 : Integer.decode(input2.getText().toString());
                            colors = colorSelected.split(" ");
                            Category newCat = new Category(input1.getText().toString(), colors[0], colors[1]);
                            //Category newCat = new Category(input1.getText().toString(), colors[0], colors[1], rank);
                            db.addOne(newCat);
                            catAdapter.notifyDataSetChanged();
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
                Intent categories2main = new Intent(CategoriesActivity.this, MainActivity.class);
                startActivity(categories2main);
            }
        });
    }



    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(db.getCategories(), fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            List<Category> catList=db.getCategories();
            Collections.swap(catList, fromPosition, toPosition);
            List<Category> newCatList=catList;
            for(int i=0;i<db.getCategories().size();i++){
                newCatList.get(i).setCategoryRank(i);
                db.updateOne(catList.get(i),newCatList.get(i));
            }
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    } ;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        colorSelected = resources[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static void setAdapter(RecyclerView recyclerView, Context context) {
        layoutManager = new LinearLayoutManager(context);
        catAdapter = new CatAdapter(db.getCategories(),context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(catAdapter);
        recyclerView.setHasFixedSize(true);
    }
}