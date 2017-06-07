package com.github.w_kamil.walizka.packingList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.w_kamil.walizka.MainListAdapter;
import com.github.w_kamil.walizka.R;
import com.github.w_kamil.walizka.dao.PackingList;
import com.github.w_kamil.walizka.dao.PackingListDao;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    public static final String PACKING_LIST = "packingList";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    PackingListDao dao;
    PackingList packingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        dao = new PackingListDao(this);
        packingList = getIntent().getExtras().getParcelable(PACKING_LIST);
        updateUI();
    }

    public static Intent createIntent(Context context, PackingList packingList) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(PACKING_LIST, packingList);
        return intent;
    }

    private void updateUI() {

        List<SinglePackingListItem> list = dao.fetchAllItemsInList(packingList);
        PackingListAdapter adapter = new PackingListAdapter(list);
        recyclerView.setAdapter(adapter);
    }


}
