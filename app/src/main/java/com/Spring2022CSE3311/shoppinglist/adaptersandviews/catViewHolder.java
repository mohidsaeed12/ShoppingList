package com.Spring2022CSE3311.shoppinglist.adaptersandviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.R;

public class catViewHolder extends RecyclerView.ViewHolder {
    // Declaring View items
    private final TextView catItemView;
    private static TextView catNameTxt;
    private static TextView catItemTxt;

    // Constructor
    public catViewHolder(@NonNull View itemView) {
        super(itemView);
        catItemView=itemView.findViewById(R.id.textView);
        catNameTxt=itemView.findViewById(R.id.cat_category_name_text);
        catItemTxt=itemView.findViewById(R.id.cat_item_name_text);
    }

    // Binding to adapter
    public void bind(String cat,String item){catNameTxt.setText(cat);catItemTxt.setText(item);}

    // Preparing to inflate the layout
    private static RelativeLayout parentLayout;

    // Inflate the layout
    static catViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_recyclerview, parent, false);
        catItemTxt = view.findViewById(R.id.cat_item_name_text);
        catNameTxt = view.findViewById(R.id.cat_category_name_text);
        parentLayout = view.findViewById(R.id.parent_layout);
        return new catViewHolder(view);
    }
}
