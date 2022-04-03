package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.Spring2022CSE3311.shoppinglist.db.itemsTbl;

public class catListAdapter extends ListAdapter<itemsTbl, catViewHolder> {
    // Constructor
    public catListAdapter(@NonNull DiffUtil.ItemCallback<itemsTbl> diffCallback) {
        super(diffCallback);
    }

    @NonNull @Override
    public catViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return catViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {
        itemsTbl current=getItem(position);
        holder.bind(current.getCategory_name(),current.getItem_name());
    }

    public static class catDiff extends DiffUtil.ItemCallback<itemsTbl>{
        // Check object references
        @Override
        public boolean areItemsTheSame(itemsTbl oldItem, itemsTbl newItem) {
            return oldItem==newItem;
        }

        // Check object values
        @Override
        public boolean areContentsTheSame(itemsTbl oldItem, itemsTbl newItem) {
            return oldItem.getCatAndItem_name().equals(newItem.getCatAndItem_name());
        }
    }
}
