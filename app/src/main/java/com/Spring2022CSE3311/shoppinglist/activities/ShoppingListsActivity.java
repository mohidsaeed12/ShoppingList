package com.Spring2022CSE3311.shoppinglist.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Spring2022CSE3311.shoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.LinearLayout.VERTICAL;
import static androidx.recyclerview.widget.DiffUtil.ItemCallback;

public class ShoppingListsActivity extends AppCompatActivity {
    // Only used for logging and debugging purposes
    private static final String TAG = "ShoppingListsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

    }
}