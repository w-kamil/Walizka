package com.github.w_kamil.walizka.dao;


import android.os.Parcel;
import android.os.Parcelable;

public class PackingList implements Parcelable{

    private int id;
    private String listName;

    public PackingList(String listName) {
        this.listName = listName;
    }

    public PackingList(int id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    public PackingList(Parcel in) {

        // the order needs to be the same as in writeToParcel() method
        this.id = in.readInt();
        this.listName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(listName);
    }
    public static final Creator<PackingList> CREATOR = new Creator<PackingList>() {
        @Override
        public PackingList createFromParcel(Parcel in) {
            return new PackingList(in);
        }

        @Override
        public PackingList[] newArray(int size) {
            return new PackingList[size];
        }
    };

}
