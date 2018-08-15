package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeListActivity;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static final String RECIPES = "recipe";

    public static final String RECIPE_BUNDLE = "recipeBundle";

    public static final String CURRENT_RECIPE = "currentRecipe";

    private static int currentService = 0;

    private static int currentRecipe = 0;

    private static List<Recipe> recipes;

    public static void setRecipes(List<Recipe> recipeList) {
        recipes = recipeList;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        if (currentService == Integer.MAX_VALUE) {
            currentService = 0;
        }

        currentRecipe = WidgetRecipeProvider.getCurrentRecipe();

        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // previous button
        Intent previousIntent = RecipeWidgetService.getActionPreviousRecipeIntent(context, currentRecipe);
        PendingIntent previousPendingIntent = PendingIntent.getService(context, ++currentService, previousIntent, 0);

        // next button
        Intent nextIntent = RecipeWidgetService.getActionNextRecipeIntent(context, currentRecipe);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, ++currentService, nextIntent, 0);

        Intent serviceIntent = new Intent(context, WidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPES, new ArrayList<>(recipes));
        serviceIntent.putExtra(RECIPE_BUNDLE, bundle);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setRemoteAdapter(R.id.recipe_widget_ingredients_list, serviceIntent);

        views.setOnClickPendingIntent(R.id.recipe_widget_recipe_name, pendingIntent);
        views.setOnClickPendingIntent(R.id.recipe_widget_previous, previousPendingIntent);
        views.setOnClickPendingIntent(R.id.recipe_widget_next, nextPendingIntent);

        views.setTextViewText(R.id.recipe_widget_recipe_name, recipes.get(currentRecipe).getName());
        views.setViewVisibility(R.id.recipe_widget_loading, View.GONE);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_container);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeWidgetService.startActionOnBoot(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager widgetManager, int appWidgetIds[]) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, widgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

