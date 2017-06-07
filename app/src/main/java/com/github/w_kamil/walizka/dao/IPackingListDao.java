package com.github.w_kamil.walizka.dao;


import java.util.List;

public interface IPackingListDao {

    List<SinglePackingListItem> fetchAllItemsInList(PackingList packingList);
    long addSingleListItem(SinglePackingListItem singlePackingListItem);
    int updateIsItemPacked(SinglePackingListItem singlePackingListItem);
    int removeItemFromList(SinglePackingListItem singlePackingListItem);
    List<PackingList> fetchAllLists();
    long addNewPackingList(String listName);
    int removeExistingPackingList(PackingList packingList);
}
