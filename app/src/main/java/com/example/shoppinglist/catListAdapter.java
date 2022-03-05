package com.example.shoppinglist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class catListAdapter extends ListAdapter<itemsTbl, catViewHolder> {

    protected catListAdapter(@NonNull DiffUtil.ItemCallback<itemsTbl> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public catViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return catViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {
        itemsTbl current=getItem(position);
        holder.bind(current.getCategory_name(),current.getItem_name());
    }


    static class catDiff extends DiffUtil.ItemCallback<itemsTbl>{

        @Override
        public boolean areItemsTheSame(itemsTbl oldItem, itemsTbl newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(itemsTbl oldItem, itemsTbl newItem) {
            return oldItem.getCatAndItem_name().equals(newItem.getCatAndItem_name());
        }
    }
}
