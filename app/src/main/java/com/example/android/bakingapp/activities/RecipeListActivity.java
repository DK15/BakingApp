package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.RecipeDetailsFragment;
import com.example.android.bakingapp.fragments.RecipeListFragment;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        setAppBarTitle(getResources().getString(R.string.app_name));

        // start recipe list fragment
        if (savedInstanceState == null) {
            RecipeListFragment fragment = RecipeListFragment.newInstance(new Bundle());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_fragment_container, fragment)
                    .commit();
        }
    }

    public void setAppBarTitle(String title) {
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(title);
        }
    }

    public void setUpAppBarBackButton(boolean visible) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {

                getSupportFragmentManager().popBackStackImmediate(RecipeDetailsFragment.LOG_TAG, 0);

                // hide the back button.
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    setUpAppBarBackButton(false);
                    setAppBarTitle(getResources().getString(R.string.app_name));
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
