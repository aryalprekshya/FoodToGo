package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class GetOrderedFoodRequest {
    @SerializedName("role")
    private String role;

    public GetOrderedFoodRequest(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
