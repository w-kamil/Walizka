package com.github.w_kamil.walizka.dao;


public class SingleListItem {
    private String description;
    private boolean isPacked;

    public SingleListItem(String description) {
        this.description = description;
        isPacked = false;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPacked() {
        return isPacked;
    }
}
