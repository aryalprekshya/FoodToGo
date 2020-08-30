package com.food2go.frontend.Enums;

public enum TableStatus{

    SERVING("Serving"),
    EMPTY("Empty");

    private final String label;

    private TableStatus(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return this.label;
    }

}