package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class AuthorizeUser {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public AuthorizeUser(){}

    public AuthorizeUser(String  username, String password){
        this.username = username;
        this.password = password;
    }

    public String GetUsername(){
        return this.username;
    }

    public void SetUsername(String username){
        this.username = username;
    }

    public String GetPassword(){
        return this.password;
    }

    public void SetPassword(String password){
        this.password = password;
    }
}
