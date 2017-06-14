package com.github.w_kamil.walizka;


import android.view.View;

public interface OnListItemClickListener {
    void onEraseClick(View v, int position);
    void OnListItemClick(View v, int position);
    void OnLongListNameClick(View v, int position);
}
