package com.github.w_kamil.walizka.dao;


public class PackingList  {

    private int id;
    private String listName;

    public PackingList(String listName) {
        this.listName = listName;
    }

    public PackingList(int id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    public int getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
