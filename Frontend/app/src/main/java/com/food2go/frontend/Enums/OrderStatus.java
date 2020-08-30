package com.food2go.frontend.Enums;

public enum OrderStatus {
    ONLINE("On Line"),
    CANCEL("Cancelled"),
    COOKING("Cooking"),
    READY("Ready"),
    SERVED("Served");


    private final String label;

    private OrderStatus(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return this.label;
    }
}
