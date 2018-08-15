package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.RecipeDetailsFragment;
import com.example.android.bakingapp.fragments.RecipeListFragment;
import com.example.android.bakingapp.fragments.RecipeStepsFragment;
import com.example.android.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.recipe_details_fragment_container)
    FrameLayout detailsContainer;

    @BindView(R.id.recipe_step_fragment_container)
    FrameLayout stepContainer;

    private Recipe recipe;

    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(RecipeListFragment.RECIPE)) {
            recipe = savedInstanceState.getParcelable(RecipeListFragment.RECIPE);
        }

        if (getSupportFragmentManager().findFragmentById(R.id.recipe_details_fragment_container) == null) {
            // start recipe list fragment
            Intent intent = getIntent();
            if (intent.hasExtra(RecipeListFragment.RECIPE)) {
                recipe = intent.getParcelableExtra(RecipeListFragment.RECIPE);
            }

            if (isTablet) {
                Bundle bundle = new Bundle();
                Bundle bundle1 = new Bundle();
                bundle.putParcelable(RecipeListFragment.RECIPE, recipe);
                bundle1.putParcelable(RecipeListFragment.RECIPE, recipe);
                bundle1.putParcelable(RecipeDetailsFragment.STEP, recipe.getStepList().get(0));

                RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(bundle);
                RecipeStepsFragment stepFragment = RecipeStepsFragment.newInstance(bundle1);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_details_fragment_container, detailsFragment)
                        .add(R.id.recipe_step_fragment_container, stepFragment)
                        .commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putParcelable(RecipeListFragment.RECIPE, recipe);

                RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_details_fragment_container, fragment)
                        .commit();

                setAppBarTitle(recipe.getName());

                showRecipeDetails();
            }
        }
    }

    public void setAppBarTitle(String title) {
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0 || isTablet) {
                    finish();
                } else {
                    getSupportFragmentManager().popBackStack();
                    setAppBarTitle(recipe.getName());
                    showRecipeDetails();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment newFragment, @IdRes int containerId, String TAG, Fragment oldFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, newFragment, TAG)
                .commit();
    }

    public void addFragment(Fragment newFragment, @IdRes int containerId, String TAG) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, newFragment, TAG)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeListFragment.RECIPE, recipe);
    }

    public void showRecipeDetails() {
        detailsContainer.setVisibility(View.VISIBLE);
        stepContainer.setVisibility(View.GONE);
    }

    public void showStep() {
        detailsContainer.setVisibility(View.GONE);
        stepContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTablet) {
            finish();
        } else {
            setAppBarTitle(recipe.getName());
            showRecipeDetails();
        }
    }
}
