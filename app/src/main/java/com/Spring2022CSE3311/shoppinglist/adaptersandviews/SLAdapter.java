package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.DatabaseHelper;
import com.Spring2022CSE3311.shoppinglist.SList;
import com.Spring2022CSE3311.shoppinglist.R;
import com.Spring2022CSE3311.shoppinglist.activities.ListsActivity;
import com.Spring2022CSE3311.shoppinglist.activities.ShoppingListsActivity;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class SLAdapter extends RecyclerView.Adapter<SLAdapter.ViewHolder>{
    private static final String TAG = "CustomAdapter";

    // Lists of the list names and dates created
    private final List<SList> lists;
    private final Context context;

    // Our adapter constructor
    public SLAdapter(List<SList> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    // ViewHolder Class that is what each item is made of in the recycler.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView dateAdded;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name_text);
            dateAdded = itemView.findViewById(R.id.date_text);
            parentLayout = itemView.findViewById(R.id.layout_lists);

        }
    }

    @NonNull
    @Override
    // Creates the view holder. Not too sure how it works but tutorial and docs have this.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // Called every time something new is added to the recycler
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.listName.setText(lists.get(position).getListName());
        holder.dateAdded.setText(lists.get(position).getDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);

                editList(lists.get(position), db, context);
            }
        });
    }



    @Override
    // This just gets the size of the list
    public int getItemCount() {
        return lists.size();
    }

    private void editList(SList list, DatabaseHelper db, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Edit or Enter List");
        builder.setCancelable(true);

        LinearLayout layout=new LinearLayout(context);
        layout.setOrientation(VERTICAL);

        // Text boxes for input
        final EditText input1 = new EditText(context);
        input1.setHint("List Name");
        layout.addView(input1);

        builder.setView(layout);

        // Adding buttons to submit or cancel data
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name;
                name = (input1.getText().toString().equals(""))? list.getListName() : input1.getText().toString();
                SList newList = new SList(name);
                db.updateOne(list, newList);
                ShoppingListsActivity.setAdapter(ShoppingListsActivity.recyclerView, context);
            }
        });
        builder.setNegativeButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent lists2items=new Intent(context, ListsActivity.class);
                lists2items.putExtra("List", list);
                context.startActivity(lists2items);
            }
        });

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteOne(list);
                ShoppingListsActivity.setAdapter(ShoppingListsActivity.recyclerView, context);
            }
        });

        builder.show();
    }
}
