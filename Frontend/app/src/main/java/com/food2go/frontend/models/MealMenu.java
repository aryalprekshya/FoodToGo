package com.food2go.frontend.models;

public class MealMenu {
    String category;
    String name;
    int image;

    public MealMenu(String category, String name, int image) {
        this.category = category;
        this.name = name;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
