package com.github.w_kamil.walizka;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.github.w_kamil.walizka.dao.PackingList;
import com.github.w_kamil.walizka.dao.PackingListDao;
import com.github.w_kamil.walizka.packingList.ListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnEraseClickListener, OnListItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private PackingListDao dao;
    private List<PackingList> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        dao = new PackingListDao(this);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_add_list, null);
        AlertDialog addNewListDialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .setPositiveButton(R.string.confirm, null)
                .setNegativeButton(R.string.cancel, null)
                .create();
        addNewListDialog.setOnShowListener(dialog -> {
            Button positiveButton = addNewListDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            final EditText listNameEditText = (EditText) dialogLayout.findViewById(R.id.name_edit_text);

            positiveButton.setOnClickListener(v -> {
                        if (listNameEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                        } else {
                            dao.addNewPackingList(listNameEditText.getText().toString());
                            updateUI();
                            addNewListDialog.dismiss();
                        }
                    }
            );
        });
        addNewListDialog.show();
        return true;
    }

    private void updateUI() {
        list = dao.fetchAllLists();
        MainListAdapter adapter = new MainListAdapter(list);
        adapter.setOnListItemClickListener(this);
        adapter.setOnEraseClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnEraseClick(View v, int position) {
        AlertDialog deleteListDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.confirmation)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    dao.removeExistingPackingList(list.get(position));
                    updateUI();
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        deleteListDialog.show();
    }


    @Override
    public void OnListItemClick(View v, int position) {
        startActivity(ListActivity.createIntent(this, list.get(position)));
    }
}
