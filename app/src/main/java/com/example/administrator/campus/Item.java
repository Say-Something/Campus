package com.example.administrator.campus;
//首页消息栏，ListView的item实例
public class Item {
    private String itemName;
    private int imageId;
    private String newAction;//新通知
    private boolean isNotice;
    public Item(String name, int imageId, String newAction, Boolean isNotice) {
        this.itemName = name;
        this.imageId = imageId;
        this.newAction = newAction;
        this.isNotice = isNotice;
    }

    public String getName() {
        return itemName;
    }

    public int getImageId() {
        return imageId;
    }
    public String getNewAction() {
        return newAction;
    }

    public void setNewAction(String newAction) {
        this.newAction = newAction;
    }
    public boolean isNotice() {
        return isNotice;
    }
    public void setNotice(boolean notice) {
        isNotice = notice;
    }
}
