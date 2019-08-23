package com.ahmedco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataModel implements Serializable {


    private String ref, title, description, thumbnail ,price;
    public void setRef(String ref) {
        this.ref = ref;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getRef(){
        return ref;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getThumbnail(){
        return thumbnail;
    }
    public String getPrice(){
        return price;
    }
}
