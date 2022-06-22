package com.demo.practicleapp.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_posts_db")
public class Post {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_image_path() {
        return item_image_path;
    }

    public void setItem_image_path(String item_image_path) {
        this.item_image_path = item_image_path;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public boolean getItem_is_stock() {
        return item_is_stock;
    }

    public void setItem_is_stock(boolean item_is_stock) {
        this.item_is_stock = item_is_stock;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "item_title")
    private String item_title;

    @ColumnInfo(name = "item_category")
    private String item_category;

    @ColumnInfo(name = "item_image_path")
    private String item_image_path;

    @ColumnInfo(name = "item_price")
    private Double item_price;

    @ColumnInfo(name = "item_is_stock")
    private boolean item_is_stock;

    public Post() {
    }

    @Ignore
    public Post(String item_title, String item_category, String item_image_path, boolean item_is_stock, double item_price) {
        this.item_title = item_title;
        this.item_category = item_category;
        this.item_image_path = item_image_path;
        this.item_is_stock = item_is_stock;
        this.item_price = item_price;
    }

}
