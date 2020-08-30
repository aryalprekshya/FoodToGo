package com.food2go.frontend.models;

public class Menu {
    String category;
    String name;
    int image;
    double price;

    public Menu() {
    }

    public Menu(String category, String name, int image, double price) {
        this.category = category;
        this.name = name;
        this.image = image;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
