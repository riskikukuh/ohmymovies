package com.dicoding.ohmymovies.ui.tvshows

import androidx.core.view.size
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MyFragmentFactory
import kotlinx.android.synthetic.main.fragment_tvshows.view.*
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

    /**
     * Memastikan fragment tvshows tampil
     * Memastikan recyclerview tvshows tampil
     * memastikan recyclerview dapat di scroll sebanyak recyclerview list tvshow
     * memastikan error tidak tampil
     */
    @Test
    fun loadTvshows() {
        val factory = MyFragmentFactory()
        launchFragmentInContainer<TvshowsFragment>(
            factory = factory
        ).onFragment {
            it.view?.let{v ->
                listTvshow = v.listTvshows
            }
        }
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.fragmentTvshowsRoot))
            .check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows))
            .check(matches(isDisplayed()))
        onView(withId(R.id.listTvshows))
            .perform(RecyclerViewActions.scrollToPosition<BaseTvshowsViewHolder>(listTvshow.size))
    }

    /**
     * memastikan error tidak tampil
     * Memastikan recyclerview tvshows tampil
     * Melakukan swipe kebawah untuk mentrigger refresh
     * Memastikan recyclerview tvshows tampil
     */
    @Test
    fun refreshTvShows(){
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
        onView(withId(R.id.listTvshows))
            .perform(RecyclerViewActions.scrollToPosition<BaseTvshowsViewHolder>(listTvshow.size))
    }

}