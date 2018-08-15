package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredients;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private Recipe recipe;

    private List<Recipe> recipeList;

    public WidgetListProvider(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getBundleExtra(NewAppWidget.RECIPE_BUNDLE);
        this.recipeList = bundle.getParcelableArrayList(NewAppWidget.RECIPES);
        assert recipeList != null;
        this.recipe = recipeList.get(0);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.recipe = recipeList.get(WidgetRecipeProvider.getCurrentRecipe());
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return recipe.getIngredientList() == null ? 0 : recipe.getIngredientList().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        Ingredients ingredient = recipe.getIngredientList().get(position);
        String ingredientName = ingredient.getIngredient();
        remoteViews.setTextViewText(R.id.recipe_widget_ingredient, ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1));
        remoteViews.setTextViewText(R.id.recipe_widget_amount, formatIngredientAmount(ingredient.getQuantity(), ingredient.getMeasure()));
        return remoteViews;
    }

    public static String formatIngredientAmount(int quantity, String measure) {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity);
        sb.append(" ");
        sb.append(measure);
        return sb.toString();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
