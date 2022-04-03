package com.example.shoppinglist.adaptersandviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private static final String TAG = "CustomAdapter";

    // Lists of the list names and dates created
    private final ArrayList<List> lists;
    private final Context context;

    // Our adapter constructor
    public ListAdapter(Context context, ArrayList<List> lists) {
        this.lists = lists;
        this.context = context;
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
        holder.listName.setText(lists.get(position).getName());
        holder.dateAdded.setText(lists.get(position).getDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, lists.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    // This just gets the size of the list
    public int getItemCount() {
        return lists.size();
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
             parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
