package com.example.shoppinglist.adaptersandviews;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shoppinglist.db.shoppingListsTbl;

public class SLlistAdapter extends ListAdapter<shoppingListsTbl, slViewHolder> {

    public SLlistAdapter(@NonNull DiffUtil.ItemCallback<shoppingListsTbl> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public slViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return slViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull slViewHolder holder, int position) {
        shoppingListsTbl current=getItem(position);
        holder.bind(current.getLists(),current.getItem(),""); //the 3rd argument will be corrected when we have the other DB entity connected
    }


    public static class SLdiff extends DiffUtil.ItemCallback<shoppingListsTbl>{

        @Override
        public boolean areItemsTheSame(shoppingListsTbl oldItem, shoppingListsTbl newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(shoppingListsTbl oldItem, shoppingListsTbl newItem) {
            return oldItem.getLists().equals(newItem.getLists());
        }
    }
}
