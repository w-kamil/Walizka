package com.github.w_kamil.walizka.dao;


import android.provider.BaseColumns;

public class PackingListDbContract  {


    public static final String DB_NAME = "packingListDb";
    public static final int DB_VERSION = 1;

    public class PackingListEntry implements BaseColumns{

        public static final String TABLE = "itemsList";

        public static final String COL_ITEM_NAME = "itemName";
        public static final String COL_IS_ITEM_PACKED = "isItemPacked";
        public static final String COL_LIST_ID = "listId";
    }

    public class ListOfLists implements BaseColumns{
        public static final String TABLE = "listOfLists";

        public static final String COL_LIST_NAME = "listName";
    }

    public static String[] COLUMNS_NAMES_ITEMS = {PackingListEntry._ID, PackingListEntry.COL_ITEM_NAME, PackingListEntry.COL_IS_ITEM_PACKED, PackingListEntry.COL_LIST_ID};
    public static String[] COLUMNS_NAMES_LISTS = {ListOfLists._ID, ListOfLists.COL_LIST_NAME};


}