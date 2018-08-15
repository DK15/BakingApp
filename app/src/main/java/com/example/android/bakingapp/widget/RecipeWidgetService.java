package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.provider.RecipeProvider;

import java.util.List;

public class RecipeWidgetService extends IntentService implements RecipeProvider.RecipeProviderListener {

    private static final String ACTION_NEXT = "com.example.android.android.bakingapp.widget.action.NEXT_RECIPE";

    private static final String ACTION_PREVIOUS = "com.example.android.android.bakingapp.widget.action.PREVIOUS_RECIPE";

    private static final String ACTION_RESTART = "com.example.android.android.bakingapp.widget.action.RESTART";

    private List<Recipe> recipes;

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }


    public static Intent getActionNextRecipeIntent(Context context, int currentRecipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_NEXT);
        intent.putExtra(NewAppWidget.CURRENT_RECIPE, currentRecipe);
        return intent;
    }

    public static Intent getActionPreviousRecipeIntent(Context context, int currentRecipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_PREVIOUS);
        intent.putExtra(NewAppWidget.CURRENT_RECIPE, currentRecipe);
        return intent;
    }

    public static void startActionOnBoot(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_RESTART);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEXT.equals(action)) {
                final int currentRecipe = intent.getIntExtra(NewAppWidget.CURRENT_RECIPE, 0);
                handleActionNextRecipe(currentRecipe);
            } else if (ACTION_PREVIOUS.equals(action)) {
                final int currentRecipe = intent.getIntExtra(NewAppWidget.CURRENT_RECIPE, 0);
                handleActionPreviousRecipe(currentRecipe);
            } else if (ACTION_RESTART.equals(action)) {
                RecipeProvider.requestRecipes(this);
            }
        }
    }

    private void handleActionNextRecipe(int currentRecipe) {
        WidgetRecipeProvider.setCurrentRecipe(currentRecipe < 3 ? ++currentRecipe : currentRecipe);
        handleActionTraversal();
    }

    private void handleActionPreviousRecipe(int currentRecipe) {
        WidgetRecipeProvider.setCurrentRecipe(currentRecipe == 0 ? 0 : --currentRecipe);
        handleActionTraversal();
    }

    private void handleActionOnBoot() {
        if (recipes != null) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));

            NewAppWidget.setRecipes(recipes);
            NewAppWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds);
        }
    }

    private void handleActionTraversal() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));

        NewAppWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_container);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_ingredients_list);
    }

    @Override
    public void onComplete(List<Recipe> recipes) {
        this.recipes = recipes;
        handleActionOnBoot();
    }
}
