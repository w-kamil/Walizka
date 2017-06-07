package com.github.w_kamil.walizka.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, PackingListDbContract.DB_NAME, null, PackingListDbContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createListOfListsTable = "CREATE TABLE " + PackingListDbContract.ListOfLists.TABLE + " ("
                + PackingListDbContract.ListOfLists._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PackingListDbContract.ListOfLists.COL_LIST_NAME + " TEXT NOT NULL);";

        String createPackingItemsTable = "CREATE TABLE " + PackingListDbContract.PackingListEntry.TABLE + " ("
                + PackingListDbContract.PackingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PackingListDbContract.PackingListEntry.COL_ITEM_NAME + " TEXT NOT NULL, "
                + PackingListDbContract.PackingListEntry.COL_IS_ITEM_PACKED + " INTEGER DEFAULT 0, "
                + PackingListDbContract.PackingListEntry.COL_LIST_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + PackingListDbContract.PackingListEntry.COL_LIST_ID + ") REFERENCES "
                + PackingListDbContract.ListOfLists.TABLE + " (" + PackingListDbContract.ListOfLists._ID + "));";

        db.execSQL(createListOfListsTable);
        db.execSQL(createPackingItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PackingListDbContract.ListOfLists.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PackingListDbContract.PackingListEntry.TABLE);
        onCreate(db);
    }
}
