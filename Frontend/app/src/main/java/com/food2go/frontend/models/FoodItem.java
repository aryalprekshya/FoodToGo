package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class FoodItem {

    @SerializedName("foodId")
    private String id;
    @SerializedName("foodName")
    private String name;
    @SerializedName("category")
    private String foodType;
    @SerializedName("quantity")
    private int quantity = 1;
    @SerializedName("price")
    private double price;
    @SerializedName("imageURL")
    private String url;

    public FoodItem(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FoodItem(String id, String name, String foodType, double price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
        this.quantity = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
