package com.github.w_kamil.walizka.dao;


public class SinglePackingListItem {

    private int id;
    private String itemName;
    private boolean isPacked;
    private final int listId;


    public SinglePackingListItem(String itemName, boolean isPacked, int listId) {
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listId = listId;
    }

    public SinglePackingListItem(int id, String itemName, boolean isPacked, int listId) {
        this.id = id;
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public int getListId() {
        return listId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }
}
