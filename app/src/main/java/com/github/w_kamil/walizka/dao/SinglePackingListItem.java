package com.github.w_kamil.walizka.dao;


public class SinglePackingListItem {

    private int id;
    private String description;
    private boolean isPacked;
    private final int listId;


    public SinglePackingListItem(String description, boolean isPacked, int listId) {
        this.description = description;
        this.isPacked = isPacked;
        this.listId = listId;
    }

    public SinglePackingListItem(int id, String description, boolean isPacked, int listId) {
        this.id = id;
        this.description = description;
        this.isPacked = isPacked;
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public int getListId() {
        return listId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }
}
