package cz.sucharda.gamesbacklogmanager.Model.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.sucharda.gamesbacklogmanager.GameDetailsActivity;
import cz.sucharda.gamesbacklogmanager.Model.Game;
import cz.sucharda.gamesbacklogmanager.R;

public class GameListItemAdapter extends RecyclerView.Adapter<GameListItemAdapter.ViewHolder> {

    final Context context;
    private final List<Game> gameArrayList;

    public GameListItemAdapter(List<Game> list, Context context) {
        gameArrayList = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return gameArrayList.size();
    }

    private Game getItem(int position) {
        return gameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gameArrayList.get(position).getId();
    }

    public void updateList(List<Game> newlist) {
        // Set new updated list
        gameArrayList.clear();
        gameArrayList.addAll(newlist);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_game_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Populate the row
        holder.populateRow(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView platform;
        private final TextView status;
        private final TextView date;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.sgi_title);
            platform = (TextView) view.findViewById(R.id.sgi_platform);
            status = (TextView) view.findViewById(R.id.sgi_status);
            date = (TextView) view.findViewById(R.id.sgi_date_added);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GameDetailsActivity.class);
            // Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Game selectedGame = getItem(getAdapterPosition());
            intent.putExtra("selectedGame", selectedGame);
            // Open GameDetailsActivity
            context.startActivity(intent);
        }

        public void populateRow(Game game) {
            title.setText(game.getTitle());
            platform.setText(game.getPlatform());
            status.setText(game.getGameStatus());
            date.setText(game.getDateAdded());
        }
    }
}