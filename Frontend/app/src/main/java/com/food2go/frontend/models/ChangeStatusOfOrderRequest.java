package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class ChangeStatusOfOrderRequest {
    @SerializedName("transactionId")
    public String transactionId;

    @SerializedName("orderId")
    public String orderId;

    @SerializedName("status")
    public String status;

    public ChangeStatusOfOrderRequest(String transactionId, String orderId, String status){
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.status = status;
    }
}
