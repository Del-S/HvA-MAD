package cz.sucharda.gamesbacklogmanager;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import cz.sucharda.gamesbacklogmanager.Model.Adapters.GameListItemAdapter;
import cz.sucharda.gamesbacklogmanager.Model.DB.DBCRUD;
import cz.sucharda.gamesbacklogmanager.Model.Dialogs.ConfirmDeleteDialog;
import cz.sucharda.gamesbacklogmanager.Model.Game;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    public static final String TIME_FORMAT = "dd/MM/yyyy";
    private List<Game> games;
    private GameListItemAdapter gameListItemAdapter;
    private RecyclerView gameListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameListView = (RecyclerView) findViewById(R.id.cm_gameList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        gameListView.setLayoutManager(mLayoutManager);
        gameListView.setHasFixedSize(true);

        // Swipe animation
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100L);
        itemAnimator.setRemoveDuration(100L);
        gameListView.setItemAnimator(itemAnimator);

        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        games = dbcrud.getGames();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivity(intent);
            }
        });

        addGestures();
        updateUI(games);
    }

    private void updateUI(List<Game> games) {
        if( gameListItemAdapter == null ) {
            gameListItemAdapter = new GameListItemAdapter(games, this);
            gameListView.setAdapter(gameListItemAdapter);
        } else {
            gameListItemAdapter.updateList(games);
            gameListItemAdapter.notifyDataSetChanged();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", getString(R.string.dialog_game_deletion_all));
            bundle.putString("positiveButton", getString(R.string.dialog_game_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Create a DBCRUD object, and pass it the context of this activity
        DBCRUD dbcrud = new DBCRUD(this);
        // Get the list of games from Database
        games = dbcrud.getGames();
        gameListItemAdapter.updateList(games);
        gameListItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DBCRUD dbcrud = new DBCRUD(this);
        dbcrud.deleteAllGames();
        showAllGamesDeletedToast();
        //finish();
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void showAllGamesDeletedToast() {
        Context context = getApplicationContext();
        String text = getString(R.string.all_games_deleted);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void addGestures() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // Swap the Cards
                Collections.swap(games, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Notify adapter Content has changed
                gameListItemAdapter.updateList(games);
                gameListItemAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // Create a DBCRUD object, and pass it the context of this activity
                DBCRUD dbcrud = new DBCRUD(MainActivity.this);
                // Delete the list of games from Database
                dbcrud.deleteAllGames();
                for (Game game : games) {
                    dbcrud.saveGame(game);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                // Create a DBCRUD object, and pass it the context of this activity
                DBCRUD dbcrud = new DBCRUD(MainActivity.this);
                // Delete the list of games from Database
                dbcrud.deleteGame(games.get(viewHolder.getAdapterPosition()).getId());
                // Remove game from temporary list
                games.remove(viewHolder.getAdapterPosition());
                // Notify adapter Content has changed
                gameListItemAdapter.updateList(games);
                gameListItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                gameListItemAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), games.size());
                // Display toast with Feedback
                //showToast(getString(R.string.swipe_delete));
                Context context = getApplicationContext();
                String text = String.format(getString(R.string.swipe_delete));
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(gameListView);
    }
}
