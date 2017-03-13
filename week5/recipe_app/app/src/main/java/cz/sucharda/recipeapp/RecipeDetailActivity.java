package cz.sucharda.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

import cz.sucharda.recipeapp.Fragments.RecipeDetailFragment;
import cz.sucharda.recipeapp.Model.Recipe;
import cz.sucharda.recipeapp.Model.RecipeSource;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE_ID = "cz.sucharda.recipeapp.recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        RecipeSource recipeSource = RecipeSource.getInstance();
        List<Recipe> recipes = recipeSource.getRecipes();
        RecipePagerAdapter recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager(), recipes);

        ViewPager recipePager = (ViewPager) findViewById(R.id.view_pager_recipe);
        recipePager.setAdapter(recipePagerAdapter);
    }

    private class RecipePagerAdapter extends FragmentStatePagerAdapter {

        private List<Recipe> recipes;

        public RecipePagerAdapter(FragmentManager fm, List<Recipe> recipes) {
            super(fm);
            this.recipes = recipes;
        }

        @Override
        public Fragment getItem(int position) {
            Recipe recipe = recipes.get(position);
            return RecipeDetailFragment.newInstance(recipe.getId());
        }

        @Override
        public int getCount() {
            return recipes.size();
        }
    }

    public static Intent newIntent(Context packageContext, UUID recipeId) {
        Intent intent = new Intent(packageContext, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }
}
