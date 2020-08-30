package com.food2go.frontend.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddNewTablesRequest {

    @SerializedName("listTable")
    public ArrayList<Table> listTable;

    public AddNewTablesRequest(ArrayList<Table> listTable){
        this.listTable = listTable;
    }

}
