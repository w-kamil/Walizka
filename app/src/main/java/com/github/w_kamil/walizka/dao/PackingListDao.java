package com.github.w_kamil.walizka.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class PackingListDao implements IPackingListDao {

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private Cursor cursor;

    public PackingListDao(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    @Override
    public List<SinglePackingListItem> fetchAllItemsInList(PackingList packingList) {
        

        return null;
    }

    @Override
    public long addSingleListItem(SinglePackingListItem singlePackingListItem) {
        return 0;
    }

    @Override
    public int updateIsItemPacked(SinglePackingListItem singlePackingListItem) {
        return 0;
    }

    @Override
    public int removeItemFromList(SinglePackingListItem singlePackingListItem) {
        return 0;
    }

    @Override
    public List<PackingList> fetchAllLists() {
        return null;
    }

    @Override
    public long addNewPackingList(String listName) {
        return 0;
    }

    @Override
    public int removeExistingPackingList(PackingList packingList) {
        return 0;
    }

    private class DbContentProvider {


        protected Cursor query(String tableName, String[] columnNames, String selection, String[] selectionArgs) {

            return database.query(tableName, columnNames, selection, selectionArgs, null, null, null);
        }

        protected long insert(String tableName, ContentValues values) {
            return database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
        }

        protected int delete(SQLiteDatabase db, String tableName, String selectionString, String[] selectionArgs) {
            return database.delete(tableName, selectionString, selectionArgs);
        }

        protected int update(SQLiteDatabase db, String tableName, ContentValues values, String whereClause, String[] wherenArgs) {
            return database.update(tableName, values, whereClause, wherenArgs);
        }

    }
}
