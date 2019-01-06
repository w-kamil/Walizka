package com.github.w_kamil.walizka.dao;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

import static com.github.w_kamil.walizka.dao.Category.OTHER;

public class SinglePackingListItem implements Comparable<SinglePackingListItem>, Parcelable {

    private int id;
    private String itemName;
    private boolean isPacked;
    private final String listName;
    private Category itemCategory;
    private boolean isSelected;


    public SinglePackingListItem(String itemName, boolean isPacked, String listName) {
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listName = listName;
        itemCategory = OTHER;
        this.isSelected = false;
    }

    SinglePackingListItem(int id, String itemName, boolean isPacked, String listName, Category itemCategory, boolean isSelected) {
        this.id = id;
        this.itemName = itemName;
        this.isPacked = isPacked;
        this.listName = listName;
        this.itemCategory = itemCategory;
        this.isSelected = isSelected;
    }

    private SinglePackingListItem(Parcel in) {
        id = in.readInt();
        itemName = in.readString();
        isPacked = in.readByte() != 0;
        listName = in.readString();
        itemCategory = Category.valueOf(in.readString());
        isSelected = in.readByte() != 0;
    }

    public static final Creator<SinglePackingListItem> CREATOR = new Creator<SinglePackingListItem>() {
        @Override
        public SinglePackingListItem createFromParcel(Parcel in) {
            return new SinglePackingListItem(in);
        }

        @Override
        public SinglePackingListItem[] newArray(int size) {
            return new SinglePackingListItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isPacked() {
        return isPacked;
    }

    String getListName() {
        return listName;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(@NonNull SinglePackingListItem o) {
        int categoryCompare = (this.itemCategory.compareTo(o.itemCategory));
        int isPackedCompare = this.isPacked() == o.isPacked() ? 0 : (o.isPacked() ? -1 : 1);
        if (isPackedCompare == 0 && categoryCompare == 0) {
            return this.itemName.compareTo(o.getItemName());
        } else if (isPackedCompare == 0) {
            return categoryCompare;
        } else return isPackedCompare;
    }

    public static Comparator<SinglePackingListItem> singlePackingListItemComparator
            = SinglePackingListItem::compareTo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(itemName);
        dest.writeByte((byte) (isPacked ? 1 : 0));
        dest.writeString(listName);
        dest.writeString(String.valueOf(itemCategory));
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
