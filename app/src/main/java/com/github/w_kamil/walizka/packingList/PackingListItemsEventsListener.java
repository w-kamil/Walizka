package com.github.w_kamil.walizka.packingList;

import com.github.w_kamil.walizka.dao.SinglePackingListItem;


public interface PackingListItemsEventsListener {

    void onCategoryImageClick(SinglePackingListItem singlePackingListItem);

    void onCheckBoxClick(SinglePackingListItem packingListItem, boolean isChecked);

    void setItemSelection(SinglePackingListItem singlePackingListItem);
}
