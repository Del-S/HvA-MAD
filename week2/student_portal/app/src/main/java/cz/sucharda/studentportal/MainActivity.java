package cz.sucharda.studentportal;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cz.sucharda.studentportal.Model.Adapters.ListAdapter;
import cz.sucharda.studentportal.Model.ListItem;

public class MainActivity extends AppCompatActivity {

    private ListAdapter mAdapter;
    private List<ListItem> mItems;
    private ListView mItemsView;
    private final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItemsView = (ListView) findViewById(R.id.cm_listView_schools);
        mItems = new ArrayList<>();
        mItems.add(new ListItem("VLO", "https://home.informatica.hva.nl/vlo/"));
        mItems.add(new ListItem("DMCI", "https://ict.dmci.hva.nl/"));
        mItems.add(new ListItem("Rooster", "https://rooster.hva.nl/"));
        mItems.add(new ListItem("Resultaten", "https://resultaten.hva.nl/"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPortalActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        mItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                ListItem item = (ListItem) mItemsView.getAdapter().getItem(position);
                i.putExtra("url", item.getUrl());
                startActivity(i);
            }
        });

        mItemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mItems.remove(position);
                updateUI();
                return true;
            }
        });

        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                ListItem item = (ListItem) data.getSerializableExtra("newItem");
                mItems.add(item);
                updateUI();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI() {
        if(mAdapter == null) {
            mAdapter = new ListAdapter(this, android.R.layout.simple_list_item_1, mItems);
            mItemsView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            mItems.clear();
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
