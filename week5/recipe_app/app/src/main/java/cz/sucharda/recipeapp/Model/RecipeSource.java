package cz.sucharda.recipeapp.Model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeSource {

    private static RecipeSource sRecipe;
    private List<Recipe> mRecipes;

    public static RecipeSource getInstance() {
        if(sRecipe == null) {
            sRecipe = new RecipeSource();
        }
        return sRecipe;
    }

    private RecipeSource() {
        mRecipes = new ArrayList<>();
        mRecipes.add(new Recipe("1. Spaghetti", "Some placeholder description."));
        mRecipes.add(new Recipe("2. Hamburger", "Some placeholder description."));
        mRecipes.add(new Recipe("3. Meatballs", "Some placeholder description."));
    }

    public void addRecipe(Recipe r) {
        mRecipes.add(r);
    }

    public void removeRecipe(Recipe r) {
        mRecipes.remove(r);
    }

    public void removeRecipeByPosition(int p) {
        mRecipes.remove(p);
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public Recipe getRecipeByPosition(int p) {
        return mRecipes.get(p);
    }

    @Nullable
    public Recipe getRecipeById(UUID id) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId().equals(id)) {
                return recipe;
            }
        }
        return null;
    }

    public void modifyRecipe(int p, Recipe r) {
        mRecipes.add(p, r);
    }
}
