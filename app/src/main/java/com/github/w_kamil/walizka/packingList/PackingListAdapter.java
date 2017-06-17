package com.github.w_kamil.walizka.packingList;


import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private List<SinglePackingListItem> list;
    private OnCheckBoxChangedListener onCheckBoxChangedListener;
    private OnLongListItemClickListener onLongListItemClickListener;
    private OnCategoryImageClickListener onCategoryImageClickListener;

    private boolean longClickSelectionFlag;

    public PackingListAdapter(List<SinglePackingListItem> list) {
        this.list = list;
    }

    public void setOnCheckBoxChangedListener(OnCheckBoxChangedListener onCheckBoxChangedListener) {
        this.onCheckBoxChangedListener = onCheckBoxChangedListener;
    }

    public void setOnLongListItemClickListener(OnLongListItemClickListener onLongListItemClickListener) {
        this.onLongListItemClickListener = onLongListItemClickListener;
    }

    public void setOnCategoryImageClickListener(OnCategoryImageClickListener onCategoryImageClickListener) {
        this.onCategoryImageClickListener = onCategoryImageClickListener;
    }


    @Override
    public PackingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_packing_list_item, parent, false);
        return new MyViewHolder(layout);
    }


    @Override
    public void onBindViewHolder(PackingListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getItemName());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.name.setOnLongClickListener(null);
        holder.itemImageView.setOnClickListener(null);
        holder.itemImageView.setImageResource(list.get(position).getItemCategory().getDrawingResource());
        holder.checkBox.setChecked(list.get(position).isPacked());

        if (list.get(position).isSelected()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
        } else if (holder.checkBox.isChecked()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorPackedListItem));
        } else {
            holder.singleItemLineraLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            onCheckBoxChangedListener.onCheckBoxClick(buttonView, position, isChecked);
        });
        holder.name.setOnLongClickListener(v -> {
            if (!longClickSelectionFlag) {
                holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
                onLongListItemClickListener.setSelectecListItem(list.get(position));
                longClickSelectionFlag = true;
                return onLongListItemClickListener.updateMenu();
            } else {
                return false;
            }
        });
        holder.itemImageView.setOnClickListener(v -> {
            
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox checkBox;
        ImageView itemImageView;
        LinearLayout singleItemLineraLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            itemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            singleItemLineraLayout = (LinearLayout) itemView.findViewById(R.id.single_item_linear_layout);
        }
    }
}
