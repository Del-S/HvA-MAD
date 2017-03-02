package cz.sucharda.basketoffruit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.sucharda.basketoffruit.Model.Adapters.ItemAdapter;
import cz.sucharda.basketoffruit.Model.ListItem;

public class ListActivity extends AppCompatActivity {

    private ListView mListView;
    private List<ListItem> mItems;
    private ItemAdapter mItemAdapter;
    private final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this, NewItemActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        mItems = new ArrayList<>();
        mItems.add(new ListItem("Apple", "Granny Smith", R.drawable.apple));
        mItems.add(new ListItem("Banana", "Chiquita banana", R.drawable.banana));

        mListView = (ListView) findViewById(R.id.lw_content);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListActivity.this, DetailsActivity.class);
                // Parse data to activity
                ListItem clickedItem = (ListItem) parent.getItemAtPosition(position);
                i.putExtra("item", clickedItem);
                // Start activity
                startActivity(i);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mItemAdapter.remove(mItems.get(position));
                updateUI();
                return true;
            }
        });
        
        updateUI();
    }

    private void updateUI() {
        if(mItemAdapter == null) {
            mItemAdapter = new ItemAdapter(this, R.layout.row_layout, mItems);
            mListView.setAdapter(mItemAdapter);
        } else {
            mItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                ListItem item = (ListItem) data.getSerializableExtra("NewItem");
                mItems.add(item);
                updateUI();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            mItems.clear();
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
