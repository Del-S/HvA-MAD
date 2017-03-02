package cz.sucharda.gamesbacklogmanager;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cz.sucharda.gamesbacklogmanager.Model.DB.DBCRUD;
import cz.sucharda.gamesbacklogmanager.Model.Dialogs.ConfirmDeleteDialog;
import cz.sucharda.gamesbacklogmanager.Model.Game;

public class ModifyGameActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private EditText mTitle, mPlatform, mNotes;
    private Spinner mStatus;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadWidgets();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyGame();
            }
        });
    }

    private void loadWidgets() {
        mTitle = (EditText) findViewById(R.id.cmg_title);
        mPlatform = (EditText) findViewById(R.id.cmg_platform);
        mNotes = (EditText) findViewById(R.id.cmg_notes);
        mStatus = (Spinner) findViewById(R.id.cmg_status);

        // Get the selected game that we've sent from GameDetailsActivity
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("selectedGame");

        mTitle.setText(game.getTitle());
        mPlatform.setText(game.getPlatform());
        mNotes.setText(game.getNotes());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_status, android.R.layout.simple_spinner_item);
        mStatus.setAdapter(statusAdapter);
        setSpinnerPosition(statusAdapter);
    }


    private void setSpinnerPosition(ArrayAdapter adapter){
        if (!game.getGameStatus().equals(null)){
            int spinnerPosition = adapter.getPosition(game.getGameStatus());
            mStatus.setSelection(spinnerPosition);
        }
    }

    void modifyGame() {
        // Get the input from the Views
        String title = mTitle.getText().toString();
        String platform = mPlatform.getText().toString();
        String gameStatus = mStatus.getSelectedItem().toString();
        String notes = mNotes.getText().toString();

        if ((title != null) && title.isEmpty()) {
            // Make EditText titleInput display an error message, and display a toast
            // That the title field is empty
            ModifyGameActivity.setErrorText(mTitle, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if ((platform != null) && platform.isEmpty()) {
            // Make EditText platformInput display an error message, and display a toast
            // That the platform field is empty
            ModifyGameActivity.setErrorText(mPlatform, getString(R.string.platform_is_required));
            showToast(getString(R.string.platform_field_is_empty));
        } else {
            // Update the game with the new data
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);
            // Create a DBCRUD object, and pass it the context of this activity
            DBCRUD dbcrud = new DBCRUD(this);
            dbcrud.modifyGame(game);
            //Notify the user of the success
            showToast(getString(R.string.game_has_been_modified));
            // Starting the previous Intent
            Intent previousActivity = new Intent(this, GameDetailsActivity.class);
            // Sending the data to GameDetailsActivity
            previousActivity.putExtra("selectedGame", game);
            setResult(1000, previousActivity);
            finish();
        }
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

    @Override
    public void onBackPressed() {
        super.onResume();  // Always call the superclass method first
        // Save game and go back to MainActivity
        modifyGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_game_cancel_single));
            bundle.putString("positiveButton", getString(R.string.dialog_game_cancel_single_confirm));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent previousActivity = new Intent(this, GameDetailsActivity.class);
        setResult(1000, previousActivity);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
