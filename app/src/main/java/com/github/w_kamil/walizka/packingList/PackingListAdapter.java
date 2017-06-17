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

    private static final int BASE_TYPE = 0;
    private static final int PACKED_TYPE = 1;
    private static final int LONG_CLICKED_SELECTED = 2;

    private List<SinglePackingListItem> list;
    private OnCheckBoxChangedListener onCheckBoxChangedListener;
    private OnLongListItemClickListener onLongListItemClickListener;
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
        holder.itemImageView.setImageResource(selectImage(list.get(position)));
        holder.checkBox.setChecked(list.get(position).isPacked());

        if (list.get(position).isSelected()) {
            holder.singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(holder.singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
        } else if (holder.checkBox.isChecked()){
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
    }


    private int selectImage(SinglePackingListItem listItem) {
        switch (listItem.getItemCategory()) {
            case CLOTHES:
                return R.drawable.ic_clothes;
            case COSMETICS:
                return R.drawable.ic_cosmetics;
            case DOCUMENTS:
                return R.drawable.ic_documents;
            case ELECTRONIC:
                return R.drawable.ic_electronic;
            case SPORT:
                return R.drawable.ic_sport;
            case PLAY:
                return R.drawable.ic_play;
            case WORK:
                return R.drawable.ic_work;
            case FOOD:
                return R.drawable.ic_food;
            default:
                return R.drawable.ic_other;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isSelected()) {
            return LONG_CLICKED_SELECTED;
        } else if (list.get(position).isPacked()) {
            return PACKED_TYPE;
        } else {
            return BASE_TYPE;
        }
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
