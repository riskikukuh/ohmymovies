package com.dicoding.ohmymovies.ui.splash

import android.content.Context
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.ohmymovies.R
//import com.dicoding.ohmymovies.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    private lateinit var context: Context

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    @Before
    fun setUp(){
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun tearDown(){
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun assertTitleAndlogo(){
        onView(withId(R.id.splashRoot))
            .check(matches(isDisplayed()))
        onView(withId(R.id.logo))
            .check(matches(withContentDescription("Logo OhMyMovies")))
        onView(withId(R.id.title))
            .check(matches(withText(context.getString(R.string.app_name))))
    }

}