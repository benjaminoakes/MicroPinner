package de.dotwee.micropinner;

import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.dotwee.micropinner.tools.PreferencesHandler;
import de.dotwee.micropinner.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static de.dotwee.micropinner.MainActivityDefaultTest.getPreferencesHandler;
import static de.dotwee.micropinner.MainActivityDefaultTest.recreateActivity;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lukas Wolfsteiner on 06.11.2015.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityThemeTest {
    private static final String LOG_TAG = "MainActivityThemeTest";

    /**
     * Preferred JUnit 4 mechanism of specifying the
     * activity to be launched before each test
     */
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    PreferencesHandler preferencesHandler;

    /**
     * This method verifies the theme-change mechanism through
     * multiple long-clicks on the header and advanced-switch.
     *
     * @throws Exception
     */
    @Test
    public void testThemeChangeMechanism() throws Exception {
        getPreferencesHandler(activityTestRule).setLightThemeEnabled(false);
        recreateActivity(activityTestRule);

        try {
            onView(withId(R.id.linearLayoutHeader))
                    .perform(longClick());
        } catch (PerformException e) {
            e.printStackTrace();
        } finally {
            assertTrue(getPreferencesHandler(activityTestRule).isLightThemeEnabled());
        }

        try {
            onView(withId(R.id.linearLayoutHeader))
                    .perform(longClick());
        } catch (PerformException e) {
            e.printStackTrace();
        } finally {
            assertFalse(getPreferencesHandler(activityTestRule).isLightThemeEnabled());
        }

        try {
            onView(withId(R.id.switchAdvanced))
                    .perform(longClick());
        } catch (PerformException e) {
            e.printStackTrace();
        } finally {
            assertTrue(getPreferencesHandler(activityTestRule).isLightThemeEnabled());
        }

        try {
            onView(withId(R.id.switchAdvanced))
                    .perform(longClick());
        } catch (PerformException e) {
            e.printStackTrace();
        } finally {
            assertFalse(getPreferencesHandler(activityTestRule).isLightThemeEnabled());
        }

    }

    /**
     * This method verifies the light theme's accent.
     */
    @Test
    public void testThemeLightAccent() throws Exception {
        preferencesHandler = getPreferencesHandler(activityTestRule);

        preferencesHandler.setLightThemeEnabled(true);
        assertTrue(preferencesHandler.isLightThemeEnabled());

        // recreate activity to apply theme
        recreateActivity(activityTestRule);

        int accentColor = activityTestRule.getActivity().getResources().getColor(R.color.accent);

        // check color for all TextView descriptions
        for (int description : new int[]{R.string.input_description_title, R.string.input_description_content, R.string.input_description_priority, R.string.input_description_visibility}) {
            onView(withText(description))
                    .check(matches(Matches.withTextColor(accentColor)));
        }
    }

    /**
     * This method verifies the light theme's background.
     */
    @Test
    public void testThemeLightBackground() throws Exception {
        preferencesHandler = getPreferencesHandler(activityTestRule);

        preferencesHandler.setLightThemeEnabled(true);
        assertTrue(preferencesHandler.isLightThemeEnabled());

        // recreate activity to apply theme
        recreateActivity(activityTestRule);

        int accentColor = activityTestRule.getActivity().getResources().getColor(R.color.background);

        onView(withId(android.R.id.content))
                .check(matches(Matches.withBackgroundColor(accentColor)));
    }

    /**
     * This method verifies the light theme's accent.
     */
    @Test
    public void testThemeDarkAccent() throws Exception {
        preferencesHandler = getPreferencesHandler(activityTestRule);

        preferencesHandler.setLightThemeEnabled(false);
        assertFalse(preferencesHandler.isLightThemeEnabled());

        // recreate activity to apply theme
        recreateActivity(activityTestRule);

        // check color for all TextView descriptions
        for (int description : new int[]{R.string.input_description_title, R.string.input_description_content, R.string.input_description_priority, R.string.input_description_visibility}) {
            onView(withText(description))
                    .check(matches(Matches.withTextColor(activityTestRule.getActivity().getResources().getColor(R.color.accent_dark))));
        }
    }

    /**
     * This method verifies the dark theme's background.
     */
    @Test
    public void testThemeDarkBackground() throws Exception {
        preferencesHandler = getPreferencesHandler(activityTestRule);

        preferencesHandler.setLightThemeEnabled(false);
        assertFalse(preferencesHandler.isLightThemeEnabled());

        // recreate activity to apply theme
        recreateActivity(activityTestRule);

        onView(withId(android.R.id.content))
                .check(matches(Matches.withBackgroundColor(activityTestRule.getActivity().getResources().getColor(R.color.background_dark))));
    }
}
