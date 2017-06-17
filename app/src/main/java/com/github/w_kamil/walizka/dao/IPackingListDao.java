package com.github.w_kamil.walizka.dao;


import java.util.List;

public interface IPackingListDao {

    List<SinglePackingListItem> fetchAllItemsInList(PackingList packingList);
    long addSingleListItem(SinglePackingListItem singlePackingListItem);
    int updateIsItemPacked(SinglePackingListItem singlePackingListItem);
    int updateItemCategory(SinglePackingListItem singlePackingListItem, Category newCategory);
    int updateIsItemSelected(SinglePackingListItem singlePackingListItem);
    int renameListItem (SinglePackingListItem singlePackingListItem, String newName);
    int removeItemFromList(SinglePackingListItem singlePackingListItem);
    List<PackingList> fetchAllLists();
    long addNewPackingList(String listName);
    int renameList(PackingList packingList, String newListName);
    int removeExistingPackingList(PackingList packingList);
}
