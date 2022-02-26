package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class slViewHolder extends RecyclerView.ViewHolder {
    private final TextView slItemView;
    private final TextView slNameTxt;
    private final TextView slItemTxt;
    private final TextView slCatTxt;

    public slViewHolder(@NonNull View itemView) {
        super(itemView);
        slItemView=itemView.findViewById(R.id.textView);
        slNameTxt=itemView.findViewById(R.id.list_name_text);
        slItemTxt=itemView.findViewById(R.id.item_name_text);
        slCatTxt=itemView.findViewById(R.id.category_name_text);
    }

    public void bind(String text){
        slItemView.setText(text);
    }
    public void bind(String list,String item,String cat){slNameTxt.setText(list);slItemTxt.setText(item);slCatTxt.setText(cat);}

    private TextView listName;
    private TextView itemName;
    private TextView catName;
    private RelativeLayout parentLayout;
    slViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sl_recyclerview, parent, false);
        listName = view.findViewById(R.id.list_name_text);
        itemName = view.findViewById(R.id.item_name_text);
        catName = view.findViewById(R.id.category_name_text);
        parentLayout = view.findViewById(R.id.parent_layout);
        return new slViewHolder(view);
    }
}
