package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        private final CheckBox cb_itemObtained;
        private final ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_amount = itemView.findViewById(R.id.tv_itemAmount);
            cb_itemObtained=itemView.findViewById(R.id.cb_itemObtained);

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
        holder.tv_amount.setText(Integer.toString(items.get(position).getItemQuantity()));
        holder.cb_itemObtained.setChecked(items.get(position).getItemObtained());


        if(items.get(position).getItemObtained()==true){
            holder.tv_itemName.setTextColor(0xFF000000);
            holder.tv_amount.setTextColor(0xFF000000);
            holder.tv_itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_amount.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.parentLayout.setBackgroundColor(0xFFFFFFFF);
        }
        else {
            holder.tv_itemName.setTextColor(Integer.decode(items.get(position).getItemCategory().getTextColor()) + 0xFF000000);
            holder.tv_amount.setTextColor(Integer.decode(items.get(position).getItemCategory().getTextColor()) + 0xFF000000);
            holder.tv_itemName.setPaintFlags(holder.tv_itemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tv_amount.setPaintFlags(holder.tv_amount.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.parentLayout.setBackgroundColor(Integer.decode(items.get(position).getItemCategory().getBackgroundColor()) + 0xFF000000);
        }

        holder.cb_itemObtained.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                items.get(position).setItemObtained(items.get(position).getItemObtained()==false);

                db.updateOne(items.get(position),items.get(position));
                ListsActivity.setAdapter(ListsActivity.recyclerView, context);
                notifyDataSetChanged();
            }
        });

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
        input1.setHint("Item Name");
        layout.addView(input1);

        final EditText input2 = new EditText(context);
        input2.setHint("Item Quantity");
        layout.addView(input2);

        final Spinner input3 = new Spinner(context);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, ListsActivity.categoryNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input3.setAdapter(arrayAdapter);
        input3.setOnItemSelectedListener(listener);
        layout.addView(input3);

        builder.setView(layout);

        // Adding buttons to submit or cancel data
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name;
                int quant;
                name = (input1.getText().toString().equals(""))? item.getItemName() : input1.getText().toString();
                quant= (input2.getText().toString().equals(""))? item.getItemQuantity() : Integer.decode(input2.getText().toString()).intValue();
                Item newItem = new Item(name,quant,item.getItemObtained() ,ListsActivity.categories.get(position), item.getListID());
                db.updateOne(item, newItem);
                ListsActivity.setAdapter(ListsActivity.recyclerView, context);
                notifyDataSetChanged();
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
