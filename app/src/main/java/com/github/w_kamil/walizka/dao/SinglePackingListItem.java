package com.github.w_kamil.walizka.dao;


import android.support.annotation.NonNull;

import java.util.Comparator;

import static com.github.w_kamil.walizka.dao.Category.OTHER;

public class SinglePackingListItem implements Comparable<SinglePackingListItem> {

    private int id;
    private String itemName;
    private boolean isPacked;
    private final int listId;
    private Category itemCategory;
    private boolean isSelected;


    public SinglePackingListItem(String itemName, boolean isPacked, int listId) {
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listId = listId;
        itemCategory = OTHER;
        this.isSelected = false;
    }

    public SinglePackingListItem(int id, String itemName, boolean isPacked, int listId, Category itemCategory, boolean isSelected) {
        this.id = id;
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listId = listId;
        this.itemCategory = itemCategory;
        this.isSelected = isSelected;
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

    public Category getItemCategory() {
        return itemCategory;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public void setItemCategory(Category itemCategory){
        this.itemCategory = itemCategory;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(@NonNull SinglePackingListItem o) {
        int categoryCompare = (this.itemCategory.compareTo(o.itemCategory));
        if((this.itemCategory.compareTo(o.itemCategory)) == 0){
            return (this.isPacked() == o.isPacked()) ? 0 : (o.isPacked() ? -1 : 1);
        } else {
            return categoryCompare;
        }
    }

    public static Comparator<SinglePackingListItem> singlePackingListItemComparator
            = new Comparator<SinglePackingListItem>() {

        @Override
        public int compare(SinglePackingListItem o1, SinglePackingListItem o2) {
            return o1.compareTo(o2);
        }
    };
}
