package com.github.w_kamil.walizka.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
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
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(PackingListDbContract.PackingListEntry.TABLE, PackingListDbContract.COLUMNS_NAMES_ITEMS,
                PackingListDbContract.PackingListEntry.COL_LIST_ID + " = ?", new String[]{String.valueOf(packingList.getId())});
        List<SinglePackingListItem> itemsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(PackingListDbContract.PackingListEntry._ID);
            int id = cursor.getInt(indexId);
            int indexName = cursor.getColumnIndex(PackingListDbContract.PackingListEntry.COL_ITEM_NAME);
            String itemName = cursor.getString(indexName);
            int indexIsPacked = cursor.getColumnIndex(PackingListDbContract.PackingListEntry.COL_IS_ITEM_PACKED);
            boolean isPacked = (cursor.getInt(indexIsPacked) == 1);
            int indexListId = cursor.getColumnIndex(PackingListDbContract.PackingListEntry.COL_LIST_ID);
            int listId = cursor.getInt(indexListId);
            itemsList.add(new SinglePackingListItem(id, itemName, isPacked, listId));
        }
        cursor.close();
        database.close();
        return itemsList;
    }

    @Override
    public long addSingleListItem(SinglePackingListItem singlePackingListItem) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PackingListDbContract.PackingListEntry.COL_ITEM_NAME, singlePackingListItem.getItemName());
        contentValues.put(PackingListDbContract.PackingListEntry.COL_IS_ITEM_PACKED, singlePackingListItem.isPacked());
        contentValues.put(PackingListDbContract.PackingListEntry.COL_LIST_ID, singlePackingListItem.getListId());
        long insertRowId = new DbContentProvider().insert(PackingListDbContract.PackingListEntry.TABLE, contentValues);
        database.close();
        return insertRowId;
    }

    @Override
    public int updateIsItemPacked(SinglePackingListItem singlePackingListItem) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PackingListDbContract.PackingListEntry.COL_IS_ITEM_PACKED, singlePackingListItem.isPacked());
        int updatedRows = new DbContentProvider().update(PackingListDbContract.PackingListEntry.TABLE, contentValues,
                PackingListDbContract.PackingListEntry._ID + " = ?", new String[]{String.valueOf(singlePackingListItem.getId())});
        database.close();
        return updatedRows;

    }

    @Override
    public int removeItemFromList(SinglePackingListItem singlePackingListItem) {
        database = dbHelper.getWritableDatabase();
        int deletedRows = new DbContentProvider().delete(PackingListDbContract.PackingListEntry.TABLE, PackingListDbContract.PackingListEntry._ID,
                new String[]{String.valueOf(singlePackingListItem.getId())});
        return deletedRows;
    }

    @Override
    public List<PackingList> fetchAllLists() {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(PackingListDbContract.ListOfLists.TABLE, PackingListDbContract.COLUMNS_NAMES_LISTS,
                null, null);
        List<PackingList> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(PackingListDbContract.ListOfLists._ID);
            int id = cursor.getInt(indexId);
            int indexListName = cursor.getColumnIndex(PackingListDbContract.ListOfLists.COL_LIST_NAME);
            String listName = cursor.getString(indexListName);
            list.add(new PackingList(id, listName));
        }
        cursor.close();
        database.close();
        return list;
    }

    @Override
    public long addNewPackingList(String listName) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PackingListDbContract.ListOfLists.COL_LIST_NAME, listName);
        long insertedRowId = new DbContentProvider().insert(PackingListDbContract.ListOfLists.TABLE, contentValues);
        database.close();
        return insertedRowId;
    }

    @Override
    public int removeExistingPackingList(PackingList packingList) {
        database = dbHelper.getWritableDatabase();
        //jak połączyć w jedno wyrażenie usunięcie listy z listy głównej z usunięciem wszystkich jej składników na liście głównej?
        new DbContentProvider().delete(PackingListDbContract.PackingListEntry.TABLE, PackingListDbContract.PackingListEntry.COL_LIST_ID + " = ?",
                new String[]{String.valueOf(packingList.getId())});
        int deletedRows = new DbContentProvider().delete(PackingListDbContract.ListOfLists.TABLE, PackingListDbContract.ListOfLists._ID + " = ?",
                new String[]{String.valueOf(packingList.getId())});
        return deletedRows;
    }

    private class DbContentProvider {


        protected Cursor query(String tableName, String[] columnNames, String selection, String[] selectionArgs) {

            return database.query(tableName, columnNames, selection, selectionArgs, null, null, null);
        }

        protected long insert(String tableName, ContentValues values) {
            return database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
        }

        protected int delete(String tableName, String selectionString, String[] selectionArgs) {
            return database.delete(tableName, selectionString, selectionArgs);
        }

        protected int update(String tableName, ContentValues values, String whereClause, String[] wherenArgs) {
            return database.update(tableName, values, whereClause, wherenArgs);
        }

    }
}
