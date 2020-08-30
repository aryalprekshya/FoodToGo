package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class FoodImage {
    @SerializedName("id")
    private String foodId;
    @SerializedName("imageURL")
    private String imageURL;

    public String getFoodId() { return this.foodId; }

    public void setFoodId(String foodId){ this.foodId = foodId;}

    public String getImageURL(){ return this.imageURL; }

    public void setImageURL(String imageURL){ this.imageURL = imageURL; }
}
