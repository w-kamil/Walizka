package com.github.w_kamil.walizka.packingList;


import android.view.View;

public interface OnCheckBoxChangedListener {
    void onCheckBoxClick(View v, int position, boolean isChecked);
}
