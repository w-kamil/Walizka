package com.github.w_kamil.walizka.packingList;

import com.github.w_kamil.walizka.dao.SinglePackingListItem;


public interface PackingListItemsEventsListener {
    void setSelectecListItem(SinglePackingListItem singlePackingListItem);

    void changeMenuToOptional();

    void onCategoryImageClick(SinglePackingListItem singlePackingListItem);

    void onCheckBoxClick(SinglePackingListItem packingListItem, boolean isChecked);
}
