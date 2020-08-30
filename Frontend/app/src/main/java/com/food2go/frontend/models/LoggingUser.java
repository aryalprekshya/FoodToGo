package com.food2go.frontend.models;


public class LoggingUser {
    private static UserInfo userInfo;

    public static UserInfo getUserInfo(){
        if(userInfo == null)
            userInfo = new UserInfo();

        return userInfo;
    }

    public static void setUserInfo(UserInfo info){
        userInfo = info;
    }

    public static void logout(){
        userInfo = null;
    }
}
