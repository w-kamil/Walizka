package com.github.w_kamil.walizka.packingList;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.w_kamil.walizka.R;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.List;

public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.MyViewHolder> {

    private SortedList<SinglePackingListItem> list;
    private PackingListItemsEventsListener packingListItemsEventsListener;

    private boolean longClickSelectionFlag;

    public PackingListAdapter(List<SinglePackingListItem> list) {
        this.list = new SortedList<>(SinglePackingListItem.class, new CustomCallback(this));
        this.list.addAll(list);
    }

    public void setPackingListItemsEventsListener(PackingListItemsEventsListener packingListItemsEventsListener) {
        this.packingListItemsEventsListener = packingListItemsEventsListener;
    }

    @NonNull
    @Override
    public PackingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_packing_list_item, parent, false);
        return new MyViewHolder(layout);
    }

    void addSinglePakingListItem(String itemName, String listName) {
        list.add(new SinglePackingListItem(itemName, false, listName));
    }

    void removeSinglePackingListItem(int position) {
        list.removeItemAt(position);
    }

    @Override
    public void onBindViewHolder(@NonNull PackingListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getItemName());
        holder.itemImageView.setImageResource(list.get(position).getItemCategory().getDrawingResource());
        holder.checkBox.setChecked(list.get(position).isPacked());
        if (list.get(position).isSelected()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
        } else if (holder.checkBox.isChecked()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorPackedListItem));
        } else {
            holder.singleItemLineraLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setLongClickSelectionFlag(boolean longClickSelectionFlag) {
        this.longClickSelectionFlag = longClickSelectionFlag;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox checkBox;
        ImageView itemImageView;
        LinearLayout singleItemLineraLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text_view);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemImageView = itemView.findViewById(R.id.item_image_view);
            singleItemLineraLayout = itemView.findViewById(R.id.single_item_linear_layout);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                packingListItemsEventsListener.onCheckBoxClick(buttonView, getAdapterPosition(), isChecked);
            });
            name.setOnLongClickListener(v -> {
                if (!longClickSelectionFlag) {
                    list.get(getAdapterPosition()).setSelected(true);
                    singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
                    packingListItemsEventsListener.setSelectecListItem(list.get(MyViewHolder.this.getAdapterPosition()), MyViewHolder.this.getAdapterPosition());
                    setLongClickSelectionFlag(true);
                    packingListItemsEventsListener.chageMentuToOptional();
                    return true;
                } else {
                    return false;
                }
            });
            itemImageView.setOnClickListener(v -> {
                packingListItemsEventsListener.onCategoryImageClick(v, getAdapterPosition());
            });
        }
    }

    static class CustomCallback extends SortedListAdapterCallback<SinglePackingListItem> {

        CustomCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(SinglePackingListItem item1, SinglePackingListItem item2) {
            return item1.compareTo(item2);
        }

        @Override
        public boolean areContentsTheSame(SinglePackingListItem item1, SinglePackingListItem item2) {
            return item1.compareTo(item2) == 0;
        }

        @Override
        public boolean areItemsTheSame(SinglePackingListItem item1, SinglePackingListItem item2) {
            return item1.equals(item2);
        }
    }
}
