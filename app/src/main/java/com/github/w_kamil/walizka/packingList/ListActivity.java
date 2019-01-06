package com.github.w_kamil.walizka.packingList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.w_kamil.walizka.R;
import com.github.w_kamil.walizka.dao.Category;
import com.github.w_kamil.walizka.dao.PackingListDao;
import com.github.w_kamil.walizka.dao.SinglePackingListItem;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements OnCheckBoxChangedListener, OnLongListItemClickListener, OnCategoryImageClickListener {

    public static final String PACKING_LIST = "packingListName";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    PackingListDao dao;
    String packingListName;
    boolean optionalMenuViewFlag;
    Menu menu;
    private ArrayList<SinglePackingListItem> list;
    private SinglePackingListItem selectedListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        dao = new PackingListDao(this);
        packingListName = getIntent().getStringExtra(PACKING_LIST);
        setTitle(packingListName);
        updateUI();
    }

    public static Intent createIntent(Context context, String packingListName) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(PACKING_LIST, packingListName);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        optionalMenuViewFlag = false;
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
                                    dao.addSingleListItem(new SinglePackingListItem(listItemNameEditText.getText().toString(), false, packingListName));
                                    updateUI();
                                    addNewListItemDialog.dismiss();
                                }
                            }
                    );
                });
                addNewListItemDialog.show();
                return true;
            case R.id.reset_packing_list:
                AlertDialog resetListDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setPositiveButton(R.string.confirm, (dialog, which) -> {
                            resetList();
                            updateUI();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                resetListDialog.show();
                return true;
            case R.id.remove_list_item:
                AlertDialog deleteItemDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setPositiveButton(R.string.confirm, (dialog, which) -> {
                            dao.removeItemFromList(selectedListItem);
                            updateUI();
                            updateMenu();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                deleteItemDialog.show();
                return true;
            case R.id.rename_list_item:
                LayoutInflater renameDialogInflater = getLayoutInflater();
                View renameDialogLayout = renameDialogInflater.inflate(R.layout.dialog_rename, null);
                AlertDialog renameListItemDialog = new AlertDialog.Builder(this)
                        .setView(renameDialogLayout)
                        .setPositiveButton(R.string.confirm, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                renameListItemDialog.setOnShowListener(dialog -> {
                    Button positiveButton = renameListItemDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText newNameEditText = (EditText) renameDialogLayout.findViewById(R.id.new_name_edit_text);
                    newNameEditText.setText(selectedListItem.getItemName());
                    positiveButton.setOnClickListener(v -> {
                                if (newNameEditText.getText().length() == 0) {
                                    Toast.makeText(this, getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                                } else {
                                    dao.renameListItem(selectedListItem, newNameEditText.getText().toString());
                                    updateUI();
                                    updateMenu();
                                    renameListItemDialog.dismiss();
                                }
                            }
                    );
                });
                renameListItemDialog.show();
                return true;
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


    @Override
    public void onCheckBoxClick(View v, int position, boolean isChecked) {
        if (optionalMenuViewFlag) {
            updateMenu();
        }
        list.get(position).setPacked(isChecked);
        dao.updateIsItemPacked(list.get(position));
        updateUI();
    }


    @Override
    public void setSelectecListItem(SinglePackingListItem singlePackingListItem) {
        singlePackingListItem.setSelected(true);
        this.selectedListItem = singlePackingListItem;
    }

    @Override
    public void onCategoryImageClick(View v, int position) {
        Category[] categories = new Categories().getCategories();
        ListAdapter adapter = new ArrayAdapter<Category>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                categories){
            @NonNull
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView textView = (TextView)v.findViewById(android.R.id.text1);
                textView.setText(categories[position].getCategoryName());
                textView.setCompoundDrawablesWithIntrinsicBounds(categories[position].getDrawingResource(), 0, 0, 0);
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                textView.setCompoundDrawablePadding(dp5);
                return v;
            }
        };
        AlertDialog chooseCategoryDialog = new AlertDialog.Builder(this)
                .setAdapter(adapter, (dialog, which) -> {
                    dao.updateItemCategory(list.get(position), categories[which]); // TODO fit data names in database
                    updateUI();
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        chooseCategoryDialog.show();
    }

    @Override
    public boolean updateMenu() {
        menu.clear();
        if (!optionalMenuViewFlag) {
            getMenuInflater().inflate(R.menu.optional_list_activity_menu, menu);
            optionalMenuViewFlag = true;
        } else {
            getMenuInflater().inflate(R.menu.list_activity_menu, menu);
            optionalMenuViewFlag = false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (optionalMenuViewFlag) {
            updateMenu();
            updateUI();
        } else {
            super.onBackPressed();
        }
    }

    private void updateUI() {
        list = new ArrayList<>();
        list.addAll(dao.fetchAllItemsInList(packingListName));
        Collections.sort(list, SinglePackingListItem.singlePackingListItemComparator);
        PackingListAdapter adapter = new PackingListAdapter(list);
        adapter.setOnCheckBoxChangedListener(this);
        adapter.setOnLongListItemClickListener(this);
        adapter.setOnCategoryImageClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
