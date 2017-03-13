package cz.sucharda.recipeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.sucharda.recipeapp.Adapters.RecipeAdapter;
import cz.sucharda.recipeapp.Model.Recipe;
import cz.sucharda.recipeapp.Model.RecipeSource;
import cz.sucharda.recipeapp.R;

public class RecipeListFragment extends Fragment {

    private RecipeListCallback mCallback;

    public interface RecipeListCallback {
        void onRecipeClicked(Recipe recipe);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecipeSource recipeSource = RecipeSource.getInstance();
        List<Recipe> mRecipes = recipeSource.getRecipes();

        View recipeListView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecyclerView recipeRecycler = (RecyclerView) recipeListView.findViewById(R.id.frl_recipe_list);
        recipeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recipeRecycler.setAdapter(new RecipeAdapter(container.getContext(), mRecipes));
        return recipeListView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (RecipeListCallback) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.getPackageName()
                    + " must implement RecipeListCallback.");
        }
    }

}
