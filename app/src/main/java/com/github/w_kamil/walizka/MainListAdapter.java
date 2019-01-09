package com.github.w_kamil.walizka;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.w_kamil.walizka.dao.PackingList;

import java.util.List;


public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyVieHolder> {

    private List<PackingList> list;
    private OnListItemClickListener onListItemClickListener;


    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public MainListAdapter(List<PackingList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_card, parent, false);
        return new MyVieHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, int position) {
        holder.packageListName.setText(list.get(position).getListName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addPackingList(String packingListName) {
        list.add(new PackingList(packingListName));
        notifyItemInserted(getItemCount() - 1);
    }

    void renamePackingList(int position, String packingListName) {
        list.get(position).setListName(packingListName);
        notifyItemChanged(position);
    }

    void removePackingList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyVieHolder extends RecyclerView.ViewHolder {

        TextView packageListName;
        ImageView removeItemImageView;

        public MyVieHolder(View itemView) {
            super(itemView);
            packageListName = itemView.findViewById(R.id.list_name_text_view);
            removeItemImageView = itemView.findViewById(R.id.remove_item_image_view);
            packageListName.setOnClickListener(v -> {
                onListItemClickListener.OnListItemClick(list.get(getAdapterPosition()).getListName());
            });
            packageListName.setOnLongClickListener(v -> {
                onListItemClickListener.OnLongListNameClick(list.get(getAdapterPosition()).getListName(), getAdapterPosition());
                return false;
            });
            removeItemImageView.setOnClickListener(v -> {
                onListItemClickListener.onEraseClick(list.get(getAdapterPosition()).getListName(), getAdapterPosition());
            });
        }
    }
}
