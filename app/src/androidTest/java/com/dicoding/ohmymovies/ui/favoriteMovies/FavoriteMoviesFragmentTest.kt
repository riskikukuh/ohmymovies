package com.dicoding.ohmymovies.ui.favoriteMovies

import androidx.core.view.size
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MyFragmentFactory
import kotlinx.android.synthetic.main.fragment_favorite_movies.view.*
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteMoviesFragmentTest {

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Memastikan layout fragment dan recyclerview movies tampil dan scroll ke posisi paling terakhir
     */
    @Test
    fun loadMovies() {
        lateinit var listMovie: RecyclerView
        val factory = MyFragmentFactory()
        launchFragmentInContainer<FavoriteMoviesFragment>(
            factory = factory
        ).onFragment {
            it.view?.let { v ->
                listMovie = v.listFavoriteMovie
            }
        }
        Espresso.onView(ViewMatchers.withId(R.id.error))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.fragmentFavoriteMoviesRoot))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.listFavoriteMovie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.listFavoriteMovie))
            .perform(
                RecyclerViewActions.scrollToPosition<FavoriteMoviesAdapter.ViewHolder>(
                    listMovie.size
                )
            )
    }

    /**
     * Memastikan recyclerview tvshow tidak tampil dan gambar empty tampil
     */
    @Test
    fun dataFavoriteMoviesKosong() {
        val factory = MyFragmentFactory()
        launchFragmentInContainer<FavoriteMoviesFragment>(
            factory = factory
        )
        Espresso.onView(ViewMatchers.withId(R.id.error))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.empty))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.listFavoriteMovie))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())))
    }
}