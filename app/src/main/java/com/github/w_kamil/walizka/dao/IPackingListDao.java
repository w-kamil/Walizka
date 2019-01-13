package com.github.w_kamil.walizka.dao;


import java.util.List;

public interface IPackingListDao {

    List<SinglePackingListItem> fetchAllItemsInList(String packingListName);
    long addSingleListItem(SinglePackingListItem singlePackingListItem);
    int updateIsItemPacked(int itemId, boolean isPacked);
    int updateItemCategory(SinglePackingListItem singlePackingListItem, Category newCategory);
    int renameListItem (SinglePackingListItem singlePackingListItem, String newName);
    int removeItemFromList(SinglePackingListItem singlePackingListItem);
    List<PackingList> fetchAllLists();
    long addNewPackingList(String listName);
    int renameList(String oldListName, String newListName);
    int removeExistingPackingList(String packingList);
}
