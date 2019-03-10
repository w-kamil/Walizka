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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements PackingListItemsEventsListener {

    public static final String PACKING_LIST = "packingListName";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PackingListDao dao;
    private String packingListName;
    private Menu menu;
    private SinglePackingListItem selectedListItem;
    private PackingListAdapter adapter;

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
        selectedListItem = null;
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
                    final EditText listItemNameEditText = dialogLayout.findViewById(R.id.item_name_edit_text);
                    positiveButton.setOnClickListener(v -> {
                                if (validateEnteredName(listItemNameEditText.getText().toString())) {
                                    SinglePackingListItem singlePackingListItem = new SinglePackingListItem(listItemNameEditText.getText().toString(), false, packingListName);
                                    dao.addSingleListItem(singlePackingListItem);
                                    adapter.addSinglePakingListItem(singlePackingListItem.getItemName(), singlePackingListItem.getListName());
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
                            adapter.removeSinglePackingListItem(selectedListItem);
                            clearMenu();
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
                    final EditText newNameEditText = renameDialogLayout.findViewById(R.id.new_name_edit_text);
                    newNameEditText.setText(selectedListItem.getItemName());
                    positiveButton.setOnClickListener(v -> {
                                if (newNameEditText.getText().toString().equals(selectedListItem.getItemName())) {
                                    Toast.makeText(this, getResources().getString(R.string.enter_new_name), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (validateEnteredName(newNameEditText.getText().toString())) {
                                    dao.renameListItem(selectedListItem, newNameEditText.getText().toString());
                                    updateUI();
                                    clearMenu();
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

    private boolean validateEnteredName(final String enteredName) {
        if (enteredName.length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (list.stream().map(SinglePackingListItem::getItemName).anyMatch(enteredName::equals)) {
//            Toast.makeText(this, getResources().getString(R.string.item_already_exists), Toast.LENGTH_LONG).show();
//            return false;
//        }
        // TODO change list item name validation logic not to work on local items list
        return true;
    }

    @Override
    public void chageMenuToOptional() {
        menu.clear();
        getMenuInflater().inflate(R.menu.optional_list_activity_menu, menu);
        adapter.setClickable(false);
    }

    private void clearMenu() {
        menu.clear();
        selectedListItem = null;
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        adapter.setClickable(true);
    }

    private void resetList() {
        //TODO create and call dao method setting all items in current list unpacked
    }

    @Override
    public void onCheckBoxClick(SinglePackingListItem packingListItem, boolean isChecked) {
        if (selectedListItem != null) {
            clearMenu();
        }
        dao.updateIsItemPacked(packingListItem.getId(), isChecked);
        adapter.setItemPacked(packingListItem, isChecked);
    }

    @Override
    public void setSelectecListItem(SinglePackingListItem singlePackingListItem) {
        this.selectedListItem = singlePackingListItem;
    }

    @Override
    public void onCategoryImageClick(View v, int position) {
        Category[] categories = Category.values();
        ListAdapter adapter = new ArrayAdapter<Category>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                categories) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(categories[position].getCategoryName());
                textView.setCompoundDrawablesWithIntrinsicBounds(categories[position].getDrawingResource(), 0, 0, 0);
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                textView.setCompoundDrawablePadding(dp5);
                return v;
            }
        };
        AlertDialog chooseCategoryDialog = new AlertDialog.Builder(this)
                .setAdapter(adapter, (dialog, which) -> {
//                    dao.updateItemCategory(list.get(position), categories[which]); //  TODO modify changincg category logis, not to work on local items list, fit data names in database
                    updateUI();
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        chooseCategoryDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (selectedListItem != null) {
            clearMenu();
            adapter.clearItemSelection(selectedListItem);
        } else {
            super.onBackPressed();
        }
    }

    private void updateUI() {
        List<SinglePackingListItem> list = dao.fetchAllItemsInList(packingListName);
        adapter = new PackingListAdapter(list);
        adapter.setPackingListItemsEventsListener(this);
        recyclerView.setAdapter(adapter);
    }
}
