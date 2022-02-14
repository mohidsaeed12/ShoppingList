package com.example.shoppinglist;

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

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>{
    private static final String TAG = "CustomAdapter";

    // Lists of the list names and dates created
    private final ArrayList<Category> categories;
    private final Context context;

    // Our adapter constructor
    public CatAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
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
        holder.listName.setText(categories.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, categories.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    // This just gets the size of the list
    public int getItemCount() {
        return categories.size();
    }

    // ViewHolder Class that is what each item is made of in the recycler.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
