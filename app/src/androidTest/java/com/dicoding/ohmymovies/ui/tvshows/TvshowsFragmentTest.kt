package com.dicoding.ohmymovies.ui.tvshows

import android.util.Log
import androidx.core.view.size
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.ui.movies.MoviesAdapter
import com.dicoding.ohmymovies.ui.movies.MoviesFragment
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MyFragmentFactory
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.fragment_tvshows.view.*
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TvshowsFragmentTest {

    private lateinit var listTvshow : RecyclerView

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_tvshows() {
        val factory = MyFragmentFactory()
        launchFragmentInContainer<TvshowsFragment>(
            factory = factory
        ).onFragment {
            it.view?.let{v ->
                listTvshow = v.listTvshows
            }
        }
        onView(withId(R.id.fragmentTvshowsRoot))
            .check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows))
            .check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows))
            .perform(RecyclerViewActions.scrollToPosition<TvshowsAdapter.ViewHolder>(listTvshow.size))
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_swipeDownForRefresh(){
        val factory = MyFragmentFactory()
        launchFragmentInContainer<TvshowsFragment>(
            factory = factory
        ).onFragment {
            it.view?.let{v ->
                listTvshow = v.listTvshows
            }
        }
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.listTvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows)).perform(swipeDown())
        onView(withId(R.id.listTvshows)).check(matches(isDisplayed()))
    }

}