package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetRestaurantRequest {
    @SerializedName("tableName")
    public String tableName;

    @SerializedName("listFood")
    public ArrayList<FoodItem> listFood;

    public GetRestaurantRequest(String tableName, ArrayList<FoodItem> listFood){
        this.tableName = tableName;
        this.listFood = listFood;
    }

}
