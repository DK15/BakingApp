package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeActivity;
import com.example.android.bakingapp.activities.RecipeListActivity;
import com.example.android.bakingapp.models.Ingredients;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    public static final String LOG_TAG = RecipeDetailsFragment.class.getCanonicalName();

    public static final String STEP = "step";

    @BindView(R.id.recipe_details_ingredients)
    TextView ingredientsView;

    @BindView(R.id.recipe_details_steps_list)
    LinearLayout stepsList;

    private Recipe recipe;

    public static RecipeDetailsFragment newInstance(Bundle bundle) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);
        return recipeDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_details_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RecipeListFragment.RECIPE);
        } else {
            Bundle bundle = getArguments();
            if (bundle != null) {
                recipe = bundle.getParcelable(RecipeListFragment.RECIPE);
            }
        }

        if (recipe != null) {
            StringBuilder sb = new StringBuilder();
            List<Ingredients> ingredients = recipe.getIngredientList();
            for (int i = 0; i < ingredients.size(); i++) {
                sb.append(ingredients.get(i).getQuantity());
                sb.append(" ");
                sb.append(ingredients.get(i).getMeasure());
                sb.append(" ");
                sb.append(ingredients.get(i).getIngredient());
                sb.append("\n");
                ingredientsView.setText(sb);

            }

            // set up app bar
            setAppBarTitle(recipe.getName());

            // set up the recycler view
            for (int i = 0; i < recipe.getStepList().size(); i++) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View stepItem = layoutInflater.inflate(R.layout.recipe_steps_item, container, false);
                TextView stepDescriptionView = (TextView) stepItem.findViewById(R.id.recipe_details_steps_description);
                stepDescriptionView.setText(recipe.getStepList().get(i).getShort_desc());

                final int position = i;
                stepItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onStepClick(recipe.getStepList().get(position));
                    }
                });

                stepsList.addView(stepItem);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipe != null) {
            outState.putParcelable(RecipeListFragment.RECIPE, recipe);
        }
    }

    private void setAppBarTitle(String title) {
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).setAppBarTitle(title);
            ((RecipeListActivity) getActivity()).setUpAppBarBackButton(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setAppBarTitle(recipe.getName());
        }
    }

    public void onStepClick(Steps step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailsFragment.STEP, step);
        bundle.putParcelable(RecipeListFragment.RECIPE, recipe);
        RecipeStepsFragment fragment = RecipeStepsFragment.newInstance(bundle);
        if (getActivity() instanceof RecipeActivity) {
            ((RecipeActivity) getActivity()).addFragment(fragment, R.id.recipe_step_fragment_container,
                    RecipeStepsFragment.LOG_TAG);
        }

    }
}
