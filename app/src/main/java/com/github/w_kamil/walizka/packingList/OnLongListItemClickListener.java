package com.github.w_kamil.walizka.packingList;

import android.view.View;

import com.github.w_kamil.walizka.dao.SinglePackingListItem;


public interface OnLongListItemClickListener  {
    void setSelectecListItem( SinglePackingListItem singlePackingListItem);
    boolean updateMenu();
}
