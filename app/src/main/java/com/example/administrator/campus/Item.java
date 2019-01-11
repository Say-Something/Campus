package com.example.administrator.campus;

public class Item {
    private String itemName;
    private int imageId;

    public Item(String name, int imageId) {
        this.itemName = name;
        this.imageId = imageId;
    }

    public String getName() {
        return itemName;
    }

    public int getImageId() {
        return imageId;
    }
}
