package com.example.shoppinglist.adaptersandviews;

import android.app.Application;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shoppinglist.db.shoppingListsTbl;

import java.util.List;

public class SLlistAdapter extends ListAdapter<shoppingListsTbl, slViewHolder> {
    private final Context context;
    private LiveData<List<shoppingListsTbl>> SLrec;
    private final Application app;
    private final slViewModel viewModel;
    // Constructor
    public SLlistAdapter(@NonNull DiffUtil.ItemCallback<shoppingListsTbl> diffCallback, Context context, LiveData<List<shoppingListsTbl>> SLrec) {
        super(diffCallback);
        this.SLrec=SLrec;
        this.context=context;
        this.app=(Application) context.getApplicationContext();
        this.viewModel=new slViewModel(app);
    }

    @NonNull @Override
    public slViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return slViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull slViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount(){
        if(SLrec==null||SLrec.getValue()==null)
        {return -1;}
        return SLrec.getValue().size();
    }

    public static class SLdiff extends DiffUtil.ItemCallback<shoppingListsTbl>{
        // Check object references
        @Override
        public boolean areItemsTheSame(shoppingListsTbl oldItem, shoppingListsTbl newItem) {
            return oldItem==newItem;
        }

        // Check object values
        @Override
        public boolean areContentsTheSame(shoppingListsTbl oldItem, shoppingListsTbl newItem) {
            return oldItem.getLists().equals(newItem.getLists());
        }
    }
}
