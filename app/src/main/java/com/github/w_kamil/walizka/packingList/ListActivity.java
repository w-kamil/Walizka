package com.github.w_kamil.walizka.packingList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.w_kamil.walizka.R;
import com.github.w_kamil.walizka.dao.PackingList;
import com.github.w_kamil.walizka.dao.PackingListDao;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements OnCheckBoxChangedListener {

    public static final String PACKING_LIST = "packingList";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    PackingListDao dao;
    PackingList packingList;
    private ArrayList<SinglePackingListItem> list;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_list_item:
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.dialog_add_list_item, null);
                AlertDialog addNewListItemDialog = new AlertDialog.Builder(this)
                        .setView(dialogLayout)
                        .setPositiveButton(R.string.confirm, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                addNewListItemDialog.setOnShowListener(dialog -> {
                    Button positiveButton = addNewListItemDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText listItemNameEditText = (EditText) dialogLayout.findViewById(R.id.item_name_edit_text);
                    positiveButton.setOnClickListener(v -> {
                                if (listItemNameEditText.getText().length() == 0) {
                                    Toast.makeText(this, getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                                } else {
                                    dao.addSingleListItem(new SinglePackingListItem(listItemNameEditText.getText().toString(), false, packingList.getId()));
                                    updateUI();
                                    addNewListItemDialog.dismiss();
                                }
                            }
                    );
                });
                addNewListItemDialog.show();
                return true;
            case R.id.reset_packing_list:
                AlertDialog deleteListDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setPositiveButton(R.string.confirm, (dialog, which) -> {
                            resetList();
                            updateUI();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                deleteListDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetList() {
        for (SinglePackingListItem listItem : list) {
            listItem.setPacked(false);
            dao.updateIsItemPacked(listItem);
        }

    }

    private void updateUI() {
        list = new ArrayList<>();
        list.addAll(dao.fetchAllItemsInList(packingList));
        Collections.sort(list, SinglePackingListItem.singlePackingListItemComparator);
        PackingListAdapter adapter = new PackingListAdapter(list);
        adapter.setOnCheckBoxChangedListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCheckBoxClick(View v, int position, boolean isChecked) {
        list.get(position).setPacked(isChecked);
        dao.updateIsItemPacked(list.get(position));
        updateUI();
    }
}
