package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

public class OpenTableRequest {
    @SerializedName("table")
    private Table table;

    public OpenTableRequest(Table table){
        this.table = table;
    }

    public Table getTableName(){
        return this.table;
    }
}
