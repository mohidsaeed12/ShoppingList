package com.example.shoppinglist;


//THIS CLASS IS TEMPORARY
public class Category {
    private String name;
    //color is temporary but will be used to set the color of categories or something like that at
    private String color;

    public Category(String name) {
        this.name = name;
        this.color = "green";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
