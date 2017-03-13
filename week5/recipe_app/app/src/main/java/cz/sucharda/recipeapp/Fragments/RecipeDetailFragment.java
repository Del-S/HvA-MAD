package cz.sucharda.recipeapp.Fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import cz.sucharda.recipeapp.Model.Recipe;
import cz.sucharda.recipeapp.Model.RecipeSource;
import cz.sucharda.recipeapp.R;

public class RecipeDetailFragment extends  Fragment {

    private static final String ARG_RECIPE_ID = "arg_recipe_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recipeView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        UUID recipeId = (UUID) getArguments().getSerializable(ARG_RECIPE_ID);
        RecipeSource recipeSource = RecipeSource.getInstance();
        Recipe recipe = recipeSource.getRecipeById(recipeId);

        TextView mTitle = (TextView) recipeView.findViewById(R.id.frd_title);
        TextView mDescription = (TextView) recipeView.findViewById(R.id.frd_description);
        mTitle.setText(recipe.getName());
        mDescription.setText(recipe.getDescription());

        return recipeView;
    }

    public static RecipeDetailFragment newInstance(UUID recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
