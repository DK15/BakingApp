package com.example.android.bakingapp.widget;

public class WidgetRecipeProvider {

    private static int currentRecipe = 0;

    private WidgetRecipeProvider() {
    }

    public static int getCurrentRecipe() {
        return currentRecipe;
    }

    public static void setCurrentRecipe(int currentRecipe) {
        WidgetRecipeProvider.currentRecipe = currentRecipe;
    }
}
