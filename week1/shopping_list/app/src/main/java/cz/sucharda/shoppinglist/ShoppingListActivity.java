package cz.sucharda.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.sucharda.shoppinglist.Model.ShoppingItem;

public class ShoppingListActivity extends AppCompatActivity {

    private ArrayAdapter<ShoppingItem> mAdapter;
    private List<ShoppingItem> mItems;
    private ListView mListView;
    private EditText mItemName;
    private FloatingActionButton mAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItems = new ArrayList<>();
        mItems.add(new ShoppingItem("Milk"));
        mItems.add(new ShoppingItem("Butter"));
        mListView = (ListView) findViewById(R.id.lw_shopping_list);

        registerForContextMenu(mListView);

        updateUI();

        mItemName = (EditText) findViewById(R.id.et_add_shopping_item);

        mAddItem = (FloatingActionButton) findViewById(R.id.fab);
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                Snackbar.make(view, "Shopping item was added.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void addItem() {
        String newItemName = mItemName.getText().toString();
        if (!newItemName.isEmpty()) {
            ShoppingItem newItem = new ShoppingItem( mItemName.getText().toString() );
            mItems.add(newItem);
            mItemName.setText("");
            updateUI();
        } else {
            Toast.makeText(this, "Please enter a value in the text field.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        // Get data from selected item (by position)
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String clickedItem = mListView.getItemAtPosition(info.position).toString();

        // Load menu layout
        getMenuInflater().inflate(R.menu.context_menu, menu);

        // Delete button
        MenuItem deleteButton = menu.findItem(R.id.context_menu_delete_item);
        String originalTitle = deleteButton.getTitle().toString();
        deleteButton.setTitle(originalTitle + " item '" + clickedItem + "'?");
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.context_menu_delete_item) {
            mItems.remove(itemInfo.position);
            updateUI();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_delete_item) {
            mItems.clear();
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
