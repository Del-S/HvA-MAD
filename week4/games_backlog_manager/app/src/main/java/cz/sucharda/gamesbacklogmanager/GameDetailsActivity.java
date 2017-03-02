package cz.sucharda.gamesbacklogmanager;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cz.sucharda.gamesbacklogmanager.Model.DB.DBCRUD;
import cz.sucharda.gamesbacklogmanager.Model.Dialogs.ConfirmDeleteDialog;
import cz.sucharda.gamesbacklogmanager.Model.Game;

public class GameDetailsActivity extends AppCompatActivity  implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private Game mGame;
    private TextView mTitle, mPlatform, mStatus, mDate, mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadWidgets();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
            intent.putExtra("selectedGame", mGame);
            startActivityForResult(intent, 1000);
            }
        });
    }

    private void loadWidgets() {
        mTitle = (TextView) findViewById(R.id.cgd_title);
        mPlatform = (TextView) findViewById(R.id.cgd_platform);
        mStatus = (TextView) findViewById(R.id.cgd_status);
        mDate = (TextView) findViewById(R.id.cgd_date_added);
        mNotes = (TextView) findViewById(R.id.cgd_notes);

        mGame = (Game) getIntent().getSerializableExtra("selectedGame");
    }

    private void setGameView() {
        mTitle.setText(mGame.getTitle());
        mPlatform.setText(mGame.getPlatform());
        mStatus.setText(mGame.getGameStatus());
        mDate.setText(mGame.getDateAdded());
        mNotes.setText(mGame.getNotes());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Set the Game Card with the updated game
        if(data.hasExtra("selectedGame")) {
            mGame = (Game) data.getSerializableExtra("selectedGame");
        }
        setGameView(); // Your method where the textviews are set
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_game) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_game_deletion_single));
            bundle.putString("positiveButton", getString(R.string.dialog_game_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User clicked on the confirm button of the Dialog, delete the game from Database
        DBCRUD dbcrud = new DBCRUD(this);
        // We only need the id of the game to delete it
        dbcrud.deleteGame(mGame.getId());
        // Game has been deleted, go back to MainActivity
        showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing, Dialog will disappear
    }

    private void showGameDeletedToast() {
        Context context = getApplicationContext();
        String text = String.format("%s %s", mGame.getTitle(), getString(R.string.game_deleted));
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
