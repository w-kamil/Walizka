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
import com.github.w_kamil.walizka.dao.Category;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.List;

public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.MyViewHolder> {

    private SortedList<SinglePackingListItem> list;
    private PackingListItemsEventsListener packingListItemsEventsListener;
    private boolean clickable;

    PackingListAdapter(List<SinglePackingListItem> list) {
        this.list = new SortedList<>(SinglePackingListItem.class, new CustomCallback(this));
        this.list.addAll(list);
        this.clickable = true;
    }

    void setPackingListItemsEventsListener(PackingListItemsEventsListener packingListItemsEventsListener) {
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

    void removeSinglePackingListItem(SinglePackingListItem item) {
        list.remove(item);
    }

    void setItemPacked(SinglePackingListItem packingListItem, boolean isPacked) {
        int position = list.indexOf(packingListItem);
        packingListItem.setPacked(isPacked);
        list.updateItemAt(position, packingListItem);
    }

    void setItemCategory(SinglePackingListItem packingListItem, Category category){
        int position = list.indexOf(packingListItem);
        packingListItem.setItemCategory(category);
        list.updateItemAt(position, packingListItem);
    }

    void setClickable(boolean clickable) {
        this.clickable = clickable;
        notifyDataSetChanged();
    }

    void clearItemSelection(SinglePackingListItem selectedItem) {
        list.get(list.indexOf(selectedItem)).setSelected(false);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PackingListAdapter.MyViewHolder holder, int position) {
        SinglePackingListItem singlePackingListItem = list.get(position);
        holder.name.setText(singlePackingListItem.getItemName());
        holder.itemImageView.setImageResource(singlePackingListItem.getItemCategory().getDrawingResource());
        holder.checkBox.setChecked(singlePackingListItem.isPacked());
        if (singlePackingListItem.isSelected()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
        } else if (holder.checkBox.isChecked()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorPackedListItem));
        } else {
            holder.singleItemLineraLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.checkBox.setClickable(clickable);
        holder.itemImageView.setClickable(clickable);
        holder.name.setClickable(clickable);
        holder.singleItemLineraLayout.setClickable(clickable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox checkBox;
        ImageView itemImageView;
        LinearLayout singleItemLineraLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text_view);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemImageView = itemView.findViewById(R.id.item_image_view);
            singleItemLineraLayout = itemView.findViewById(R.id.single_item_linear_layout);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    packingListItemsEventsListener.onCheckBoxClick(list.get(MyViewHolder.this.getAdapterPosition()), isChecked);
                }
            });
            name.setOnLongClickListener(v -> {
                if (clickable) {
                    list.get(getAdapterPosition()).setSelected(true);
                    singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
                    packingListItemsEventsListener.setSelectecListItem(list.get(getAdapterPosition()));
                    packingListItemsEventsListener.changeMenuToOptional();
                    return true;
                } else {
                    return false;
                }
            });
            itemImageView.setOnClickListener(v -> packingListItemsEventsListener.onCategoryImageClick(list.get(getAdapterPosition())));
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
