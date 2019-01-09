package com.github.w_kamil.walizka.packingList;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

    @NonNull
    @Override
    public PackingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_packing_list_item, parent, false);
        return new MyViewHolder(layout);
    }

    void removeSinglePackingListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
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
                onCheckBoxChangedListener.onCheckBoxClick(buttonView, getAdapterPosition(), isChecked);
            });
            name.setOnLongClickListener(v -> {
                if (!longClickSelectionFlag) {
                    singleItemLineraLayout.setBackgroundColor(ContextCompat.getColor(singleItemLineraLayout.getContext(), R.color.colorListItemLongClickSelected));
                    onLongListItemClickListener.setSelectecListItem(list.get(getAdapterPosition()), getAdapterPosition());
                    longClickSelectionFlag = true;
                    return onLongListItemClickListener.updateMenu();
                } else {
                    return false;
                    // TODO modify selectionFlag solution to handle for more selections
                }
            });
            itemImageView.setOnClickListener(v -> {
                onCategoryImageClickListener.onCategoryImageClick(v, getAdapterPosition());
            });
        }
    }
}
