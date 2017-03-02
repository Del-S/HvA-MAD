package cz.sucharda.homework;

import android.content.Intent;
import android.database.Cursor;
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

import cz.sucharda.homework.Model.Adapters.HomeworkAdapter;
import cz.sucharda.homework.Model.DB.HomeworkCursorWrapper;
import cz.sucharda.homework.Model.DB.HomeworkDataSource;

public class HomeworkListActivity extends AppCompatActivity {

    private HomeworkDataSource mDataSource;
    private Cursor mCursor;
    private HomeworkAdapter mAdapter;
    private ListView mHomeworkListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHomeworkListView = (ListView) findViewById(R.id.chl_listView_homeworks);
        mDataSource = new HomeworkDataSource(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeworkListActivity.this, AddHomeworkActivity.class);
                startActivity(i);
            }
        });

        mHomeworkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeworkListActivity.this, HomeworkDetailActivity.class);
                intent.putExtra("homework_id", id);
                startActivity(intent);
            }
        });

        mHomeworkListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDataSource.deleteHomework(id);
                updateUi();
                return false;
            }
        });

        updateUi();
    }

    private void updateUi() {
        mCursor = mDataSource.getAllHomework();
        if (mAdapter == null) {
            mAdapter = new HomeworkAdapter(this, mCursor);
            mHomeworkListView.setAdapter(mAdapter);
        } else {
            mAdapter.changeCursor(mCursor);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
