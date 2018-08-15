package com.example.android.bakingapp.utils;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {
    private static final String TAG = UrlBuilder.class.getCanonicalName();

    private static final String RECIPE_URL = "http://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildRecipeQuery() {

        URL url = null;
        try {
            url = new URL(RECIPE_URL);
        } catch (MalformedURLException e) {
            Log.w(TAG, e.toString());
        }
        return url;
    }
}
