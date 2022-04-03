package com.Spring2022CSE3311.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.Spring2022CSE3311.shoppinglist.R.id;
import static com.Spring2022CSE3311.shoppinglist.R.layout;
import static com.Spring2022CSE3311.shoppinglist.R.string;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // Connecting buttons in XML file to Button objects in java class
        final Button goToCategories = findViewById(id.categories_btn);
        final Button goToShoppingLists=findViewById(id.shopping_list_btn);

        // allows any and all buttons on this activity to go through the same OnClickListener()
        View.OnClickListener homePageButtonSelector;
        homePageButtonSelector= view -> {
            // would use switch-case statement, but resource IDs are non-final, so google advises if-else instead
            if(view.getId()==id.categories_btn){
                Intent main2categories=new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(main2categories);
            }
            else if(view.getId()==id.shopping_list_btn){
                Intent main2shoppingLists=new Intent(MainActivity.this,ShoppingListsActivity.class);
                startActivity(main2shoppingLists);
            }
            else {
                throw new IllegalStateException(getString(string.Unexpected_val) + view.getId());
            }
        };

        // When a button on the main activity is pressed, one of these is run to switch to a different activity
        goToCategories.setOnClickListener(homePageButtonSelector);
        goToShoppingLists.setOnClickListener(homePageButtonSelector);
    }
}