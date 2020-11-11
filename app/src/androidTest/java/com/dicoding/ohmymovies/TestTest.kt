package com.dicoding.ohmymovies

import android.app.Application
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.ohmymovies.ui.splash.SplashActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TestTest {

    private lateinit var context: Context

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    @Before
    fun setUp(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun assertTitleAndlogo(){
        onView(withId(R.id.splashRoot)).check(matches(isDisplayed()))
        onView(withId(R.id.logo)).check(matches(withContentDescription("Logo OhMyMovies")))
        onView(withId(R.id.title)).check(matches(withText(context.getString(R.string.app_name))))
    }

}