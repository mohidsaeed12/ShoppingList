package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.RecyclerView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.Item;
import com.Spring2022CSE3311.shoppinglist.R;
import com.Spring2022CSE3311.shoppinglist.activities.ListsActivity;
import com.Spring2022CSE3311.shoppinglist.activities.ShoppingListsActivity;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements AdapterView.OnItemSelectedListener{
    // Lists of the list names and dates created
    private final List<Item> items;
    private final Context context;
    private AdapterView.OnItemSelectedListener listener;
    private int position;

    // Our adapter constructor
    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
        this.listener = this;
    }

    // ViewHolder Class that is what each item is made of in the recycler.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_itemName;
        private final TextView tv_amount;
        private final ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_amount = itemView.findViewById(R.id.tv_itemAmount);
            parentLayout = itemView.findViewById(R.id.layout_items);

        }
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_items, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    // Called every time something new is added to the recycler
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_itemName.setText(items.get(position).getItemName());
        holder.tv_itemName.setTextColor(Integer.decode(items.get(position).getItemCategory().getTextColor()) + 0xFF000000);
        holder.tv_amount.setText("1");
        holder.tv_amount.setTextColor(Integer.decode(items.get(position).getItemCategory().getTextColor()) + 0xFF000000);
        holder.parentLayout.setBackgroundColor(Integer.decode(items.get(position).getItemCategory().getBackgroundColor()) + 0xFF000000);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);

                editItem(items.get(position), db, context);
            }
        });
    }



    @Override
    // This just gets the size of the list
    public int getItemCount() {
        return items.size();
    }

    private void editItem(Item item, DatabaseHelper db, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Edit item");
        builder.setCancelable(false);

        LinearLayout layout=new LinearLayout(context);
        layout.setOrientation(VERTICAL);

        // Text boxes for input
        final EditText input1 = new EditText(context);
        input1.setHint("List Name");
        layout.addView(input1);

        final Spinner input2 = new Spinner(context);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, ListsActivity.categoryNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input2.setAdapter(arrayAdapter);
        input2.setOnItemSelectedListener(listener);
        layout.addView(input2);

        builder.setView(layout);

        // Adding buttons to submit or cancel data
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name;
                name = (input1.getText().toString().equals(""))? item.getItemName() : input1.getText().toString();
                Item newItem = new Item(name,ListsActivity.categories.get(position), item.getListID());
                db.updateOne(item, newItem);
                ListsActivity.setAdapter(ListsActivity.recyclerView, context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteOne(item);
                ShoppingListsActivity.setAdapter(ShoppingListsActivity.recyclerView, context);
                ListsActivity.setAdapter(ListsActivity.recyclerView, context);
            }
        });

        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        position = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
