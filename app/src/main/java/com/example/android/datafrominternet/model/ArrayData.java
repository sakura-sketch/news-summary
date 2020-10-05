package com.example.android.datafrominternet.model;

import org.json.JSONArray;

import java.lang.reflect.Array;

public class ArrayData {

    private JSONArray data;
    public JSONArray getData() {return data;}
    public void setData(JSONArray data) {this.data = data;}

    private static final ArrayData holder = new ArrayData();
    public static ArrayData getInstance() {return holder;}


}
