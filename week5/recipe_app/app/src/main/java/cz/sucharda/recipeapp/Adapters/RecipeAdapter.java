package cz.sucharda.recipeapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.sucharda.recipeapp.Fragments.RecipeListFragment;
import cz.sucharda.recipeapp.Model.Recipe;
import cz.sucharda.recipeapp.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> mRecipeArrayList;
    private Context mContext;
    private RecipeListFragment.RecipeListCallback mCallback;

    public RecipeAdapter(Context context, List<Recipe> mRecipes) {
        this.mContext = context;
        mRecipeArrayList = mRecipes;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_recipe_item, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, final int position) {
        holder.populateRow(mRecipeArrayList.get(position));

        mCallback = (RecipeListFragment.RecipeListCallback) mContext;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe mRecipe = getItem(position);
                mCallback.onRecipeClicked(mRecipe);
            }
        });
    }

    private Recipe getItem(int position) {
        return mRecipeArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeArrayList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitle, mDescription;
        //private RecipeListFragment.RecipeListCallback mCallback;

        //initialize the variables
        public RecipeHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.sri_title);
            mDescription = (TextView) view.findViewById(R.id.sri_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = getItem(getAdapterPosition());
            mCallback.onRecipeClicked(recipe);
        }

        public void populateRow(Recipe recipe) {
            mTitle.setText(recipe.getName());
            mDescription.setText(recipe.getDescription());
        }
    }
}
