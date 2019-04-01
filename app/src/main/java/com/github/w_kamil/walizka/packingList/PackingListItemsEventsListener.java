package com.github.w_kamil.walizka.packingList;

import android.view.View;

import com.github.w_kamil.walizka.dao.SinglePackingListItem;


public interface PackingListItemsEventsListener {
    void setSelectecListItem(SinglePackingListItem singlePackingListItem);

    void changeMenuToOptional();

    void onCategoryImageClick(View v, int position);

    void onCheckBoxClick(SinglePackingListItem packingListItem, boolean isChecked);
}
