package com.Spring2022CSE3311.shoppinglist;

public class Category {

    private int id, rank;
    private String categoryName, backgroundColor, textColor;

    public Category() {
        this.rank=1;
    }

    public Category(String categoryName, String backgroundColor, String textColor) {
        this();
        this.id = -1;
        this.categoryName = categoryName;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Category(int id, String categoryName, String backgroundColor, String textColor) {
        this(categoryName, backgroundColor, textColor);
        this.id = id;
    }

    public Category(int id, String categoryName, String backgroundColor, String textColor, int rank) {
        this(id, categoryName, backgroundColor, textColor);
        this.rank = rank;
    }

    public Category(String categoryName, String backgroundColor, String textColor, int rank) {
        this(categoryName, backgroundColor, textColor);
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", textColor='" + textColor + '\'' +
                "rank=" + rank +
                '}';
    }

    public int getCategoryRank() {
        return rank;
    }

    public void setCategoryRank(int rank) {
        this.rank = rank;
    }
}
