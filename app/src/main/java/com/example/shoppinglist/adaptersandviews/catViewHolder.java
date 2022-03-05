package com.example.shoppinglist.adaptersandviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class catViewHolder extends RecyclerView.ViewHolder {
    private final TextView catItemView;
    private final TextView catNameTxt;
    private final TextView catItemTxt;

    public catViewHolder(@NonNull View itemView) {
        super(itemView);
        catItemView=itemView.findViewById(R.id.textView);
        catNameTxt=itemView.findViewById(R.id.cat_category_name_text);
        catItemTxt=itemView.findViewById(R.id.cat_item_name_text);
    }

    public void bind(String text){
        catItemView.setText(text);
    }
    public void bind(String cat,String item){catNameTxt.setText(cat);catItemTxt.setText(item);}

    private static TextView itemName;
    private static TextView catName;
    private static RelativeLayout parentLayout;
    static catViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_recyclerview, parent, false);
        itemName = view.findViewById(R.id.cat_item_name_text);
        catName = view.findViewById(R.id.cat_category_name_text);
        parentLayout = view.findViewById(R.id.parent_layout);
        return new catViewHolder(view);
    }
}
