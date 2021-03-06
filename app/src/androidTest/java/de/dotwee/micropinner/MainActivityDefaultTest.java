package de.dotwee.micropinner;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.dotwee.micropinner.tools.PreferencesHandler;
import de.dotwee.micropinner.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityDefaultTest {
    private static final String LOG_TAG = "MainActivityDefaultTest";
    /**
     * Preferred JUnit 4 mechanism of specifying the
     * activity to be launched before each test
     */
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    PreferencesHandler preferencesHandler;

    /**
     * This method returns an instance of {@link PreferencesHandler}
     * for an activity test rule.
     *
     * @param activityTestRule Source to get the PreferenceHandler.
     * @return An instance of {@link PreferencesHandler}
     */
    static PreferencesHandler getPreferencesHandler(ActivityTestRule<MainActivity> activityTestRule) {
        return PreferencesHandler.getInstance(activityTestRule.getActivity());
    }

    /**
     * This method recreates the main activity in order to apply
     * themes or reload the preference cache.
     *
     * @param activityTestRule Source to get access to the activity.
     */
    static void recreateActivity(final ActivityTestRule<MainActivity> activityTestRule) {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });
    }

    /**
     * This method verifies the advanced-switch's functionality.
     *
     * @throws Exception
     */
    @Test
    public void testAdvancedSwitch() throws Exception {
        getPreferencesHandler(activityTestRule).setAdvancedUse(false);
        recreateActivity(activityTestRule);

        onView(withId(R.id.switchAdvanced))
                .perform(click())
                .check(matches(isChecked()));

        assertTrue(getPreferencesHandler(activityTestRule).isAdvancedUsed());

        onView(withId(R.id.switchAdvanced))
                .perform(click())
                .check(matches(not(isChecked())));

        assertFalse(getPreferencesHandler(activityTestRule).isAdvancedUsed());
    }

    /**
     * This method looks for an EditText with id = R.id.editTextTitle,
     * performs some inout and verifies the EditText's entered value.
     */
    @Test
    public void testEditTextTitle() throws Exception {
        final String value = "MicroPinner title input";

        onView(withId(R.id.editTextTitle))
                .perform(typeText(value))
                .check(matches(withText(value)));
    }

    /**
     * This method recreates the main activity and verifies the focus,
     * which should be on the title EditText.
     */
    @Test
    public void testEditTextFocus() throws Exception {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().recreate();
            }
        });

        onView(withId(R.id.editTextTitle))
                .check(matches(isFocusable()));
    }

    /**
     * This method looks for an EditText with id = R.id.editTextContent,
     * performs some inout and verifies the EditText's entered value.
     */
    @Test
    public void testEditTextContent() throws Exception {
        final String value = "MicroPinner title input";

        onView(withId(R.id.editTextContent))
                .perform(typeText(value))
                .check(matches(withText(value)));
    }

    /**
     * This method performs an empty input on the title EditText and
     * clicks on the pin-button. Verifies if a Toast appears.
     */
    @Test
    public void testEmptyTitleToast() throws Exception {

        // perform empty input
        onView(withId(R.id.editTextTitle))
                .perform(typeText(""));

        // click pin button
        onView(withText(R.string.dialog_action_pin))
                .perform(click());

        // verify toast existence
        onView(withText(R.string.message_empty_title))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * This method clicks the advanced-switch button, which should
     * hide CheckBoxes and verifies their (invisible) state.
     */
    @Test
    public void testExpandMechanism() throws Exception {
        getPreferencesHandler(activityTestRule).setAdvancedUse(false);
        recreateActivity(activityTestRule);

        // perform click on the advanced switch
        onView(withId(R.id.switchAdvanced))
                .perform(click());

        // checkBox should be not visible
        onView(withId(R.id.checkBoxPersistentPin))
                .check(matches(not(isChecked())));
    }

    /**
     * This method verifies the preference mechanism for the advanced usage.
     */
    @Test
    public void testPreferencesAdvanced() throws Exception {
        preferencesHandler = getPreferencesHandler(activityTestRule);

        preferencesHandler.setAdvancedUse(true);
        assertTrue(preferencesHandler.isAdvancedUsed());
    }
}