package com.github.w_kamil.walizka;


public interface OnListItemClickListener {
    void onEraseClick(String packingListName, int position);
    void OnListItemClick(String listName);
    void OnLongListNameClick(String listName, int position);
}
