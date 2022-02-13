package com.example.shoppinglist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private static final String TAG = "CustomAdapter";

    // Lists of the list names and dates created
    private ArrayList<String> names;
    private ArrayList<String> dates;
    private Context context;

    // Our adapter constructor
    public CustomAdapter(Context context, ArrayList<String> names, ArrayList<String> dates) {
        this.names = names;
        this.dates = dates;
        this.context = context;
    }

    @NonNull
    @Override
    // Creates the view holder. Not too sure how it works but tutorial and docs have this.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    // Called every time something new is added to the recycler
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.listName.setText(names.get(position));
        holder.dateAdded.setText(dates.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, names.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    // This just gets the size of the list
    public int getItemCount() {
        return names.size();
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
