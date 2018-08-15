package com.example.android.bakingapp.provider;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.utils.JsonUtils;
import com.example.android.bakingapp.utils.Network;
import com.example.android.bakingapp.utils.UrlBuilder;

import java.io.IOException;
import java.util.List;

public class RecipeProvider {

    private static final String TAG = RecipeProvider.class.getCanonicalName();

    private static AsyncTask mBackgroundTask;
    private static RecipeProviderListener currentListener;

    private static List<Recipe> recipes;

    /**
     * Method to get the recipes
     *
     * @param listener a {@link RecipeProviderListener}
     */
    public static synchronized void requestRecipes(final RecipeProviderListener listener) {
        currentListener = listener;
        if (recipes != null) {
            onComplete(recipes);
            return;
        }
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String response = null;
                try {
                    response = Network.getResponseFromHTTPUrl(UrlBuilder.buildRecipeQuery());
                } catch (IOException e) {
                    Log.w(TAG, e.toString());
                }

                recipes = JsonUtils.parseRecipeModels(response);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                onComplete(recipes);
            }
        };

        mBackgroundTask.execute();
    }

    protected static void onComplete(List<Recipe> recipes) {
        currentListener.onComplete(recipes);
    }

    public interface RecipeProviderListener {
        void onComplete(List<Recipe> recipes);
    }
}
