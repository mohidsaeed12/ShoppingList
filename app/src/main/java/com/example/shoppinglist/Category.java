package com.example.shoppinglist;


//THIS CLASS IS TEMPORARY
public class Category {
    private String name;
    private String textColor;
    private String fillColor;

    public Category(String name, String textColor, String fillColor) {
        this.name = name;
        this.textColor = textColor;
        this.fillColor = fillColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }
}
