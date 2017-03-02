package cz.sucharda.seriesapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import cz.sucharda.seriesapp.Adapters.SeriesAdapter;
import cz.sucharda.seriesapp.Model.DB.SeriesDataSource;
import cz.sucharda.seriesapp.Model.Series;

public class SeriesListActivity extends AppCompatActivity {

    private ListView mSeriesListView;
    private SeriesDataSource mDataSource;
    private Cursor mCursor;
    private SeriesAdapter mAdapter;
    private static final int RC_SERIAL_NEW = 1000;
    private static final int RC_SERIAL_DETAIL = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSeriesListView = (ListView) findViewById(R.id.cls_listView_series);
        mDataSource = new SeriesDataSource(this);

        mSeriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( SeriesListActivity.this, SeriesDetailActivity.class );
                i.putExtra( "series_id", id );
                startActivityForResult(i, RC_SERIAL_DETAIL);
            }
        });

        mSeriesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDataSource.deleteSeries(id);
                updateUI();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( SeriesListActivity.this, SeriesAddActivity.class );
                startActivityForResult(i, RC_SERIAL_NEW);
            }
        });

        updateUI();
    }

    public void updateUI() {
        mCursor = mDataSource.getAllSeries();
        if (mAdapter == null) {
            mAdapter = new SeriesAdapter(this, mCursor);
            mSeriesListView.setAdapter(mAdapter);
        } else {
            mAdapter.changeCursor(mCursor);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SERIAL_DETAIL) {
                Series series = (Series) data.getSerializableExtra("updatedSeries");
                mDataSource.updateSeries(series);
                updateUI();
            }

            if (requestCode == RC_SERIAL_NEW) {
                Series series = (Series) data.getSerializableExtra("newSeries");
                mDataSource.addSeries(series);
                updateUI();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_series, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            mDataSource.deleteAllSeries();
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
