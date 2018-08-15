package com.example.android.bakingapp;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.activities.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
public class ValidateRecipeListTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    Resources resources = InstrumentationRegistry.getContext().getResources();

    @Test
    /*
      This method validates the list of recipes displayed on MainActivity screen
     */
    public void validateRecipleListDisplayed() {

        onView(withText((R.string.recipe_one))).check(matches(isDisplayed()));
        onView(withText((R.string.recipe_two))).check(matches(isDisplayed()));
        onView(withText((R.string.recipe_three))).check(matches(isDisplayed()));
        onView(withText((R.string.recipe_four))).check(matches(isDisplayed()));
    }

    @Test
    /*
      This method validates that clicking on recyclerview item displays expected recipe name
      in title and checks that Ingredients and Steps sub-headings are displayed.
     */
    public void clickRecipeToValidateDetails() {

        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText((R.string.recipe_one))).check(matches(isDisplayed()));
        onView(withText((R.string.recipe_details_ingredients_title))).check(matches(isDisplayed()));
        onView(withText((R.string.steps))).check(matches(isDisplayed()));
    }
}
