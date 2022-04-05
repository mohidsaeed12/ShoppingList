package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.Spring2022CSE3311.shoppinglist.Category;
import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.activities.CategoriesActivity;
import com.Spring2022CSE3311.shoppinglist.R;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> implements AdapterView.OnItemSelectedListener{
    private List<Category> categories;
    private Context context;
    private AdapterView.OnItemSelectedListener listener;
    private String colorSelected;

    public CatAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        this.listener = this;
        this.colorSelected = "";
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_catName;
        private final ConstraintLayout layout_categories;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_catName =  view.findViewById(R.id.tv_catName);
            layout_categories = view.findViewById(R.id.layout_categories);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_catName.setText(categories.get(position).getCategoryName());
        holder.tv_catName.setTextColor(Integer.decode(categories.get(position).getTextColor()) + 0xFF000000);
        holder.layout_categories.setBackgroundColor(Integer.decode(categories.get(position).getBackgroundColor()) + 0xFF000000);

        holder.layout_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);

                editCategory(categories.get(position), db, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void editCategory(Category category, DatabaseHelper db, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Edit category");
        builder.setCancelable(false);

        LinearLayout layout=new LinearLayout(context);
        layout.setOrientation(VERTICAL);

        // Text boxes for input
        final EditText input1 = new EditText(context);
        input1.setHint("Category Name");
        layout.addView(input1);

        final Spinner input2 = new Spinner(context);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.color_names, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input2.setAdapter(arrayAdapter);
        input2.setOnItemSelectedListener(listener);
        layout.addView(input2);

        builder.setView(layout);

        // Adding buttons to submit or cancel data
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name;
                name = (input1.getText().toString().equals(""))? category.getCategoryName() : input1.getText().toString();
                String colors[];
                colors = colorSelected.split(" ");
                Category newCat = new Category(name, colors[0], colors[1]);
                db.updateOne(category, newCat);
                CategoriesActivity.setAdapter(CategoriesActivity.recyclerView, context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteOne(category);
                CategoriesActivity.setAdapter(CategoriesActivity.recyclerView, context);
            }
        });

        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        colorSelected = CategoriesActivity.resources[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
