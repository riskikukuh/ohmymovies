package com.dicoding.ohmymovies.ui.home

//import androidx.test.espresso.contrib.RecyclerViewActions
import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoExt.matchToolbarTitle
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import org.junit.After
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
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Memastikan tab layout tampil dan fragment tiap segmen tampil saat di klik
     */
    @Test
    fun displayingViewpager(){
        onView(withId(R.id.homeRootView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matchToolbarTitle(context.getString(R.string.app_name)))

        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
    }


    /**
     * Memastikan fragment tvshows tampil saat tab tv shwows di klik
     */
    @Test
    fun showTvShowsPage(){
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.fragmentTvshowsRoot)).check(matches(isDisplayed()))
    }

    /**
     * Memastikan fragment movies tampil saat tab movies di klik
     */
    @Test
    fun showMoviesPage(){
        onView(withText("MOVIES")).perform(click())
        onView(withId(R.id.fragmentMoviesRoot)).check(matches(isDisplayed()))
    }

}