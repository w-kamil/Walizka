package com.github.w_kamil.walizka;

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
    private OnEraseClickListener onEraseClickListener;
    private OnListItemClickListener onListItemClickListener;

    public void setOnEraseClickListener(OnEraseClickListener onEraseClickListener) {
        this.onEraseClickListener = onEraseClickListener;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public MainListAdapter(List<PackingList> list) {
        this.list = list;
    }

    @Override
    public MyVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_card, parent, false);
        return new MyVieHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyVieHolder holder, int position) {
        holder.packageListName.setText(list.get(position).getListName());
        holder.packageListName.setOnClickListener(v -> {
            onListItemClickListener.OnListItemClick(v, position);
        });
        holder.imageView.setOnClickListener(v -> {
            onEraseClickListener.onEraseClick(v, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyVieHolder extends RecyclerView.ViewHolder {

        TextView packageListName;
        ImageView imageView;


        public MyVieHolder(View itemView) {
            super(itemView);
            packageListName = (TextView) itemView.findViewById(R.id.list_name_text_view);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
