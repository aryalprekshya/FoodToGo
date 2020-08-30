package com.food2go.frontend.models;

import com.food2go.frontend.Utilities.DateTimeHelper;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Table {
    @SerializedName("tableId")
    private String tableId;
    @SerializedName("tableName")
    private String tableName;
    @SerializedName("tableStatus")
    private String tableStatus;
    @SerializedName("openAt")
    private Date openAt;
    @SerializedName("x")
    private int x;
    @SerializedName("y")
    private int y;
    @SerializedName("restaurant")
    private int code;

    public Table(){}

    public Table(String tableId, String tableName, String tableStatus, Date openAt, int x, int y, int code){
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.openAt = openAt;
        this.x = x;
        this.y = y;
        this.code=code;
    }

    public String getTableId(){
        return this.tableId;
    }

    public void setTableId(String tableId){
        this.tableId = tableId;
    }

    public String getTableName(){
        return this.tableName;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String getTableStatus(){
        return this.tableStatus;
    }

    public void setTableStatus(String tableStatus){
        this.tableStatus = tableStatus;
    }

    public String getOpenAt(){
        return DateTimeHelper.getTime(this.openAt);
    }

    public void setOpenAt(Date openAt){
        this.openAt = openAt;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRestaurant(int code){
        this.code = code;
    }

    public int getRestaurant(){
        return this.code;
    }
}
