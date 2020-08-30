package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class TableTransactionDetail {
    @SerializedName("transactionId")
    private String transactionId;
    @SerializedName("tableName")
    private String tableName;
    @SerializedName("status")
    private String tableStatus;
    @SerializedName("openAt")
    private Date openAt;
    @SerializedName("orderedFoods")
    private ArrayList<Order> orderedFoods;
    @SerializedName("totalAmount")
    private double totalAmount;


    public String getTransactionId(){ return this.transactionId; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getTableName() { return this.tableName; }

    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getTableStatus() { return this.tableStatus; }

    public void setTableStatus(String tableStatus) { this.tableStatus = tableStatus; }

    public Date getOpenAt() { return this.openAt; }

    public void setOpenAt(Date openAt) { this.openAt = openAt; }

    public ArrayList<Order> getOrderedFoods() { return this.orderedFoods; }

    public void setOrderedFoods(ArrayList<Order> orderedFoods){ this.orderedFoods = orderedFoods; }

    public double getTotalAmount() { return this.totalAmount; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
