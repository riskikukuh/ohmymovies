package com.dicoding.ohmymovies.ui.home

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.ui.movies.MoviesAdapter
import com.dicoding.ohmymovies.ui.movies.MoviesFragment
import com.dicoding.ohmymovies.ui.splash.SplashActivity
import com.dicoding.ohmymovies.ui.tvshows.TvshowsAdapter
import com.dicoding.ohmymovies.util.EspressoExt.matchToolbarTitle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.fragment_tvshows.view.*
import org.hamcrest.Matchers.*
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private lateinit var context: Context

    private val TAG = HomeActivityTest::class.java.simpleName

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun tabLayoutIsVisibleAndFragmentIsVisible(){
        onView(withId(R.id.homeRootView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matchToolbarTitle(context.getString(R.string.app_name)))

        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.fragmentMoviesRoot)).check(matches(isDisplayed()))

        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.fragmentTvshowsRoot)).check(matches(isDisplayed()))
    }

    @Test
    fun isListTvshowsHaveDescendantItemTvshow(){
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.fragmentTvshowsRoot)).check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows)).check(matches(hasDescendant(withId(R.id.itemTvshow))))
    }

    @Test
    fun isListMoviesHaveDescendantItemMovie(){
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.fragmentMoviesRoot)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).check(matches(hasDescendant(withId(R.id.itemMovie))))
    }

}