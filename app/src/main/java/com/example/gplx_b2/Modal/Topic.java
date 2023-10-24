package com.example.gplx_b2.Modal;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Topic implements Serializable {
    private int id;
    private String image;
    private String title;
    private int quantity; //for progress bar
    private String subTitle;

    public Topic() {}

    public Topic(int id, String image, String title, int quantity, String subTitle) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.quantity = quantity;
        this.subTitle = subTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
