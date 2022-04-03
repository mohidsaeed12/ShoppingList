package com.example.shoppinglist.adaptersandviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class slViewHolder extends RecyclerView.ViewHolder {
    // Declaring View items
    private final TextView slItemView;
    private static TextView slNameTxt;
    private static TextView slItemTxt;
    private static TextView slCatTxt;

    // Constructor
    public slViewHolder(@NonNull View itemView) {
        super(itemView);
        slItemView=itemView.findViewById(R.id.textView);
        slNameTxt=itemView.findViewById(R.id.list_name_text);
        slItemTxt=itemView.findViewById(R.id.item_name_text);
        slCatTxt=itemView.findViewById(R.id.category_name_text);
    }

    // Binding to adapter
    public void bind(slViewModel viewModel, int i){
        slNameTxt.setText(viewModel.getSLrecsSync().get(i).getLists());
        slItemTxt.setText(viewModel.getSLrecsSync().get(i).getItem());
        if(viewModel.getSLrecsSync().get(i).getItemObtained()){
            slCatTxt.setText("obtained");
        }
        else{
            slCatTxt.setText("need to get");
        }
    }

    // Preparing to inflate the layout
    private static RelativeLayout parentLayout;

    // Inflate the layout
    static slViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sl_recyclerview, parent, false);
        slNameTxt = view.findViewById(R.id.list_name_text);
        slItemTxt = view.findViewById(R.id.item_name_text);
        slCatTxt = view.findViewById(R.id.category_name_text);
        parentLayout = view.findViewById(R.id.parent_layout);
        return new slViewHolder(view);
    }
}
