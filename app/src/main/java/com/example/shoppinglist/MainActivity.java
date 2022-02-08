package com.example.shoppinglist;

import static com.example.shoppinglist.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        final Button goToCategories = findViewById(id.categories_btn);
        final Button goToShoppingLists=findViewById(id.shopping_list_btn);

        View.OnClickListener homePageButtonSelector; // allows any and all buttons on this activity to go through the same OnClickListener()
        homePageButtonSelector=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    throw new IllegalStateException("Unexpected value: " + view.getId());
                }
            }
        };
        goToCategories.setOnClickListener(homePageButtonSelector);
        goToShoppingLists.setOnClickListener(homePageButtonSelector);
    }
}