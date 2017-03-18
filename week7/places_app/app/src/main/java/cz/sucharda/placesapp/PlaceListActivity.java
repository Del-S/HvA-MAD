package cz.sucharda.placesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cz.sucharda.placesapp.adapters.PlaceAdapter;
import cz.sucharda.placesapp.model.database.PlaceProvider;

public class PlaceListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int PLACE_DETAIL_REQUEST_CODE = 1234;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        setToolbar();
        adapter = new PlaceAdapter(this, null);
        RecyclerView placeRecycler = (RecyclerView) findViewById(R.id.apl_place_view);
        placeRecycler.setLayoutManager(new LinearLayoutManager(this));
        placeRecycler.setHasFixedSize(true);
        placeRecycler.setAdapter(adapter);
        placeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.apl_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceListActivity.this, PlaceDetailActivity.class);
                startActivityForResult(intent, PLACE_DETAIL_REQUEST_CODE);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void restartLoader() {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.apl_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Places");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            restartLoader();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            showDeleteAllMessage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPlaces() {
        getContentResolver().delete(PlaceProvider.CONTENT_URI, null, null);
        restartLoader();
        // Notifying user that all items are deleted
        Toast.makeText(PlaceListActivity.this, "All items deleted", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteAllMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Delete all places")
                .setMessage("Are you sure you want to delete all of your places?")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAllPlaces();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, PlaceProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Take the data represented by the cursor object, and pass it to the cursor adaptor
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
