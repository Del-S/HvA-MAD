package cz.sucharda.recipeapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.sucharda.recipeapp.Fragments.RecipeListFragment;
import cz.sucharda.recipeapp.Model.Recipe;

public class RecipeListActivity extends AppCompatActivity implements RecipeListFragment.RecipeListCallback {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mFragmentManager = getSupportFragmentManager();
        Fragment listFragment = mFragmentManager.findFragmentById(R.id.arl_list);
        if (listFragment == null) {
            listFragment = new RecipeListFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.arl_list, listFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = RecipeDetailActivity.newIntent(this, recipe.getId());
        startActivity(intent);
    }

}
