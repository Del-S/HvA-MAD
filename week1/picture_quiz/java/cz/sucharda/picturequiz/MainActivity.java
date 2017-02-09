package cz.sucharda.picturequiz;

import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Local variables
    private int currentImageIndex = 0;
    private int[] imageNames;
    private Button mNextButton;
    private ImageView mImageView;

    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageNames = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3};
        mImageView = (ImageView) findViewById(R.id.iw_test_image);

        mNextButton = (Button) findViewById(R.id.b_next);
        mNextButton.setEnabled(false);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageIndex++;
                if (currentImageIndex >= imageNames.length) {
                    currentImageIndex = 0;
                }
                mImageView.setImageResource(imageNames[currentImageIndex]);
                mNextButton.setEnabled(false);
            }
        });

        // Fill the list view
        items = getResources().getStringArray(R.array.description_text);
        mListView = (ListView) findViewById(R.id.lw_descriptions);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentImageIndex == position) {
                    Toast.makeText(MainActivity.this, "Right choice", Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(true);
                } else {
                    Toast.makeText(MainActivity.this, "Wrong! You have to pick right choice to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateUI();
    }

    private void updateUI() {
        if(adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items );
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
