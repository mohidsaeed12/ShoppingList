package com.example.shoppinglist.adaptersandviews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class catViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "***catViewHolder: ";
    // Declaring View items
    //public final TextView catItemView;
    public static TextView catNameTxt;
    public static TextView catItemTxt;

    // Constructor
    public catViewHolder(View itemView) {
        super(itemView);
        //catItemView=itemView.findViewById(R.id.textView);
        this.catNameTxt=itemView.findViewById(R.id.cat_category_name_text);
        this.catItemTxt=itemView.findViewById(R.id.cat_item_name_text);
        Log.d(TAG, "constructor");
    }

    // Binding to adapter
    public void bind(final catViewModel viewModel, int i){
        Log.d(TAG, "bind");
        catNameTxt.setText(viewModel.getAllItemsSync().get(i).getCategory_name());
        catItemTxt.setText(viewModel.getAllItemsSync().get(i).getItem_name());
    }

    // Preparing to inflate the layout
    public static RelativeLayout parentLayout;

    // Inflate the layout
    public static catViewHolder create(ViewGroup parent){
        Log.d(TAG, "create");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_recyclerview, parent, false);
        catItemTxt = view.findViewById(R.id.cat_item_name_text);
        catNameTxt = view.findViewById(R.id.cat_category_name_text);
        parentLayout = view.findViewById(R.id.parent_layout);
        return new catViewHolder(view);
    }
}
