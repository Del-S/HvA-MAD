package cz.sucharda.gamesbacklogmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.sucharda.gamesbacklogmanager.Model.DB.DBCRUD;
import cz.sucharda.gamesbacklogmanager.Model.Game;

public class AddGameActivity extends AppCompatActivity {

    private EditText mTitle, mPlatform, mNotes;
    private Spinner mStatus;
    private static final String TIME_FORMAT = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadWidgets();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGame();
            }
        });
    }

    private void loadWidgets() {
        mTitle = (EditText) findViewById(R.id.cag_title);
        mPlatform = (EditText) findViewById(R.id.cag_platform);
        mNotes = (EditText) findViewById(R.id.cag_notes);
        mStatus = (Spinner) findViewById(R.id.cag_status);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);

        //Set the adapter to the spinner
        mStatus.setAdapter(statusAdapter);
    }

    private void saveGame() {
        String curDate = getSimpleCurrentDate();
        String title = mTitle.getText().toString();
        String platform = mPlatform.getText().toString();
        String gameStatus = mStatus.getSelectedItem().toString();
        String notes = mNotes.getText().toString();

        if ((title != null) && title.isEmpty()) {
            AddGameActivity.setErrorText(mTitle, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if ((platform != null) && platform.isEmpty()) {
            AddGameActivity.setErrorText(mPlatform, getString(R.string.platform_is_required));
            showToast(getString(R.string.platform_field_is_empty));
        } else {
            DBCRUD dbcrud = new DBCRUD(this);
            Game game = new Game(-1, title, platform, curDate, gameStatus, notes);
            dbcrud.saveGame(game);
            showToast(getString(R.string.game_has_been_added));
            finish();
        }
    }


    private static String getSimpleCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(MainActivity.TIME_FORMAT);
        Date today = new Date();

        return format.format(today);
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private static void setErrorText(EditText editText, String message) {
        int RGB = Color.argb(255, 255, 0, 0);

        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        editText.setError(ssbuilder);
    }

}
