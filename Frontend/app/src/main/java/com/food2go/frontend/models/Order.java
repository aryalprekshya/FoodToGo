package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("transactionId")
    private String transactionId;
    @SerializedName("tableName")
    private String tableName;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("foodId")
    private String foodId;
    @SerializedName("foodName")
    private String foodName;
    @SerializedName("imageURL")
    private String imageURL;
    @SerializedName("foodStatus")
    private String foodStatus;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private double price;

    public String getTransactionId() {
        return transactionId;
    }

    public void setId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderId() { return this.orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getFoodId() {
        return this.foodId;
    }

    public void setFoodId(String foodId) { this.foodId = foodId; }

    public String getFoodName() { return this.foodName; }

    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getImageURL() { return this.imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(String foodStatus) {
        this.foodStatus = foodStatus;
    }

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return this.price; }

    public void setPrice(double price) { this.price = price; }
}