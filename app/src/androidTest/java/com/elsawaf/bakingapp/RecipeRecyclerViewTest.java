package com.elsawaf.bakingapp;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.elsawaf.bakingapp.activities.MainActivity;
import com.elsawaf.bakingapp.adapters.IngredientAdapter.IngredientViewHolder;
import com.elsawaf.bakingapp.network.OkHttpProvider;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeRecyclerViewTest {

    public static final String INGREDIENT_NAME = "salt";
    public static final String RECIPE_NAME = "Nutella Pie";
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new
            ActivityTestRule<>(MainActivity.class);

    // should using IdlingResource because the list is obtained from a webservice
    @Before
    public void registerIdlingResource() {
        mIdlingResource = OkHttp3IdlingResource.create(
                "okhttp", OkHttpProvider.getOkHttpInstance(
                        InstrumentationRegistry.getTargetContext()));
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }

    /*check the first recipe name at the list
    * using atPosition() because the list is RecyclerView
    * using hasDescendant() because the list item has ViewGroup*/
    @Test
    public void recipeListItemName() {
        onView(withId(R.id.recyclerViewRecipes))
                .check(matches(atPosition(0,
                        hasDescendant(withText(RECIPE_NAME)))));

    }

    @Test
    public void clickRecipeListItem_OpenRecipeDetails() {

        // using @link scrollToPosition() to get a reference to a specific RecyclerView item and clicks on it.
        // we don't need scrollToPosition() in this case because the first item is always displayed on the screen
        onView(withId(R.id.recyclerViewRecipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks the Recipe details Activity opens with the correct title displayed
        onView(AllOf.allOf(isAssignableFrom(AppCompatTextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(RECIPE_NAME)));

        // checks that ingredients fragment is displayed
        onView(withId(R.id.ingredientsFragment)).
                check(matches(isDisplayed()));
    }


    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static Matcher<RecyclerView.ViewHolder> withHolderIngredientView(String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, IngredientViewHolder>(IngredientViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }

            @Override
            protected boolean matchesSafely(IngredientViewHolder item) {
                TextView nameTextView = item.itemView.findViewById(R.id.tvIngredientName);
                if (nameTextView == null) {
                    return false;
                }
                return nameTextView.getText().toString().contains(text);
            }
        };
    }
}
