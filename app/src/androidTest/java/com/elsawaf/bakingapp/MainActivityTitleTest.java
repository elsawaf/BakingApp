package com.elsawaf.bakingapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.elsawaf.bakingapp.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class MainActivityTitleTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new
            ActivityTestRule<>(MainActivity.class);

    /*Test if MainActivity Toolbar has the right name of the app
    * This is the first way of two option to access toolbar title*/
    @Test
    public void toolbarTitle(){
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.app_name);
        onView(ViewMatchers.isAssignableFrom(Toolbar.class))
                .check(ViewAssertions.matches(withToolbarTitle(Is.is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("With toolbar title: ");
                textMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar item) {
                return textMatcher.matches(item.getTitle());
            }
        };
    }
}
