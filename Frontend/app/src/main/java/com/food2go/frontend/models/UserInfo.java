package com.food2go.frontend.models;


import com.google.gson.annotations.SerializedName;

public class UserInfo{
    @SerializedName("_id")
    private String userId;
    @SerializedName("username")
    private String username;

    //private String displayName;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("role")
    private String role;

    public UserInfo(){}

    public UserInfo(String userId, String username, String displayName, String accessToken, String role){
        this.userId = userId;
        this.username = username;
        //this.displayName = displayName;
        this.accessToken = accessToken;
        this.role = role;
    }

    public String GetUserId(){
        return this.userId;
    }

    public void SetUserId(String userId){
        this.userId = userId;
    }

    public String GetUsername(){
        return this.username;
    }

    public void SetUsername(String username){
        this.username = username;
    }

    public String GetDisplayName(){
        return "";
        //return this.displayName;
    }

    public void SetDisplayName(String displayName){
        //this.displayName = displayName;
    }

    public String GetAccessToken(){
        return  this.accessToken;
    }

    public void SetAccessToken() {
        this.accessToken = accessToken;
    }

    public String GetRole(){
        return role;
    }

    public void SetRole(String role){
        this.role = role;
    }
}
