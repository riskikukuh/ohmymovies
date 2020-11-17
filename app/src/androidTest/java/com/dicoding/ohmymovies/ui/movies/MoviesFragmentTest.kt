package com.dicoding.ohmymovies.ui.movies

//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import org.junit.Test.mockito.Mock
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
import kotlinx.android.synthetic.main.fragment_movies.view.*
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MoviesFragmentTest {

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Memastikan layout fragment dan recyclerview movies tampil dan scroll ke posisi paling terakhir
     */
    @Test
    fun loadMovies() {
        lateinit var listMovie : RecyclerView
        val factory = MyFragmentFactory()
        launchFragmentInContainer<MoviesFragment>(
            factory = factory
        ).onFragment {
            it.view?.let{v ->
                listMovie = v.listMovie
            }
        }
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.fragmentMoviesRoot)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).perform(RecyclerViewActions.scrollToPosition<MoviesAdapter.ViewHolder>(listMovie.size))
    }

    /**
     * Memastikan layout fragment dan recyclerview movies tampil dan scroll ke posisi paling terakhir saat di refresh
     */
    @Test
    fun refreshMovies(){
        lateinit var listMovie : RecyclerView
        val factory = MyFragmentFactory()
        launchFragmentInContainer<MoviesFragment>(
            factory = factory
        ).onFragment {
            it.view?.let{v ->
                listMovie = v.listMovie
            }
        }
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).perform(swipeDown())
        onView(withId(R.id.listMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.listMovie)).perform(RecyclerViewActions.scrollToPosition<MoviesAdapter.ViewHolder>(listMovie.size))
    }

}