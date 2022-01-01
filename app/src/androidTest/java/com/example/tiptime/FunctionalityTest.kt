package com.example.tiptime


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.ceil

@RunWith(AndroidJUnit4::class)
class FunctionalityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private val amount = 123
    private val text1 = amount.toString()

    /**
     *  Calculate the default 20 percent
     */
    @Test
    fun calculate_default_tip(){

        /**
         * Do a step by step process of how the user will click the buttons
         */

        onView(withId(R.id.text_input_edit))
            .perform(typeText(text1))

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(true, 20)))))

        // put the switch in the off state
        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(false, 20)))))


    }

    /**
     * Calculate the 18 percent
     */
    @Test
    fun calculate_good_tip(){

        /**
         *  Do a step by step process of how the user will click the buttons
         */

        onView(withId(R.id.text_input_edit))
            .perform(typeText(text1))

        onView(withId(R.id.option_eighteen_percent))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(true, 18)))))

        // put the switch in the off state
        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(false, 18)))))

    }


    /**
     * Calculate the 15 percent
     */
    @Test
    fun calculate_ok_tip(){

        /**
         *  Do a step by step process of how the user will click the buttons
         */

        onView(withId(R.id.text_input_edit))
            .perform(typeText(text1))

        onView(withId(R.id.option_fifteen_percent))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(true, 15)))))

        // put the switch in the off state
        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(recheck(false, 15)))))

    }

    // Function to calculate the values
    private fun recheck(roundup : Boolean, percent : Int): String {

        // Convert the value to a decimal
        val value = percent * 0.01

        // Calculate the tip
        var tip = value * amount

        // Round up the value if the switch is checked
        if(roundup) {
            tip = ceil(tip)
        }
        return "£${tip}"
    }
}