package com.example.administrator.campus;

public class CampusItem {
    private String itemName;
    private int imageId;
    private int type;
    public CampusItem(String itemName, int imageId, int type) {
        this.itemName = itemName;
        this.imageId = imageId;
        this.type = type;
    }
    public String getItemName() {
        return itemName;
    }

    public int getImageId() {
        return imageId;
    }
    public int getType() {
        return type;
    }
}
