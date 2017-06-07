package com.github.w_kamil.walizka.packingList;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.w_kamil.walizka.R;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.List;

public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.MyViewHolder> {

    private List<SinglePackingListItem> list;

    public PackingListAdapter(List<SinglePackingListItem> list) {
        this.list = list;
    }

    @Override
    public PackingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_packing_list_item, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(PackingListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getItemName());
        holder.checkBox.setChecked(list.get(position).isPacked());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }


}
