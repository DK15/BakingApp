package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeActivity;
//import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.adapters.RecipeListAdapter;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.provider.RecipeProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeProvider.RecipeProviderListener, RecipeListAdapter.RecipeOnClickListener {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;

    @BindView(R.id.recipe_list_loading)
    ProgressBar progressBar;

    @BindView(R.id.recipe_list_error_message)
    TextView errorMessage;


    public static final String RECIPES = "recipes";

    public static final String RECIPE = "recipe";

    private RecipeListAdapter recipeListAdapter;

    private List<Recipe> recipeList;

    private boolean isTablet;

    public RecipeListFragment() {
    }

    /**
     * Method to retrieve a new instance of RecipeListFragment.
     */
    public static RecipeListFragment newInstance(Bundle bundle) {
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundle);
        return recipeListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(RECIPES);
        } else {
            recipeList = args.getParcelableArrayList(RECIPES);
        }
        if (recipeList == null) {
            //  showProgressBar();
            recipeListAdapter = new RecipeListAdapter(getContext(), this);
            RecipeProvider.requestRecipes(this);
        } else {
            recipeListAdapter = new RecipeListAdapter(getContext(), this, recipeList);
            showRecipes();
        }
        if (isTablet) {
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else {
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        recipeRecyclerView.setAdapter(recipeListAdapter);

        return view;
    }

    @Override
    public void onComplete(List<Recipe> recipes) {
        if (recipes == null) {
            //   showError();
        } else {
            recipeList = recipes;
            recipeListAdapter.setRecipes(recipeList);
            showRecipes();
        }
    }

    private void showRecipes() {
        progressBar.setVisibility(View.GONE);
        recipeRecyclerView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeList != null) {
            outState.putParcelableArrayList(RECIPES, new ArrayList<>(recipeList));
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        intent.putExtra(RECIPE, recipe);
        startActivity(intent);
    }
}
