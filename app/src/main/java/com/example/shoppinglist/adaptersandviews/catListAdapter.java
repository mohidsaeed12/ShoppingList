package com.example.shoppinglist.adaptersandviews;

import android.app.Application;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shoppinglist.db.itemsTbl;

import java.util.List;

public class catListAdapter extends ListAdapter<itemsTbl, catViewHolder> {
    private final Context context;
    private LiveData<java.util.List<itemsTbl>> catAndItem;
    private final Application app;
    private final catViewModel viewModel;


    // Constructor
    public catListAdapter(@NonNull DiffUtil.ItemCallback<itemsTbl> diffCallback, Context context, LiveData<List<itemsTbl>> catAndItem) {
        super(diffCallback);
        this.catAndItem=catAndItem;
        this.context=context;
        this.app=(Application) context.getApplicationContext();
        this.viewModel=new catViewModel(app);
    }

    @NonNull @Override
    public catViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return catViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {
        holder.bind(viewModel, position);

        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click detected", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount(){
        if(catAndItem==null||catAndItem.getValue()==null)
            {return -1;}
        return catAndItem.getValue().size();
    }

    public LiveData<List<itemsTbl>> getCatAndItem() {
        final LiveData<List<itemsTbl>> catAndItem = this.catAndItem;
        return catAndItem;
    }

    public void setCatAndItem(LiveData<List<itemsTbl>> catAndItem) {
        this.catAndItem = catAndItem;
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
