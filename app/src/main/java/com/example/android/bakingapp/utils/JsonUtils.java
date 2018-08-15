package com.example.android.bakingapp.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingapp.models.Ingredients;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getCanonicalName();

    @Nullable
    public static List<Recipe> parseRecipeModels(String response) {
        if (response == null) {
            return null;
        }

        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray recipesJSONArray = new JSONArray(response);
            // loop through the array of recipes, building a Recipe
            for (int i = 0; i < recipesJSONArray.length(); i++) {

                Recipe recipe = new Recipe();
                JSONObject recipeJSONObject = recipesJSONArray.getJSONObject(i);
                recipe.setId(recipeJSONObject.getInt("id"));
                recipe.setName(recipeJSONObject.getString("name"));
                recipe.setServings(recipeJSONObject.getInt("servings"));
                recipe.setImage(recipeJSONObject.getString("image"));

                JSONArray ingredientJSONArray = recipeJSONObject.getJSONArray("ingredients");
                List<Ingredients> ingredientList = new ArrayList<>();
                // loop through the array of ingredients
                for (int j = 0; j < ingredientJSONArray.length(); j++) {
                    Ingredients ingredient = new Ingredients();

                    JSONObject ingredientJSONObject = ingredientJSONArray.getJSONObject(j);
                    ingredient.setQuantity(ingredientJSONObject.getInt("quantity"));
                    ingredient.setMeasure(ingredientJSONObject.getString("measure"));
                    ingredient.setIngredient(ingredientJSONObject.getString("ingredient"));

                    ingredientList.add(ingredient);
                }

                recipe.setIngredientList(ingredientList);

                JSONArray stepJSONArray = recipeJSONObject.getJSONArray("steps");
                List<Steps> stepList = new ArrayList<>();
                //loop through the array of steps
                for (int j = 0; j < stepJSONArray.length(); j++) {
                    Steps step = new Steps();

                    JSONObject stepJSONObject = stepJSONArray.getJSONObject(j);
                    step.setId(stepJSONObject.getInt("id"));
                    step.setShort_desc(stepJSONObject.getString("shortDescription"));
                    step.setDesc(stepJSONObject.getString("description"));
                    step.setVideo_url(stepJSONObject.getString("videoURL"));
                    step.setThumbnail(stepJSONObject.getString("thumbnailURL"));

                    stepList.add(step);
                }

                recipe.setStepList(stepList);

                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            Log.w(LOG_TAG, e.toString());
            return null;
        }

        return recipeList;
    }
}

