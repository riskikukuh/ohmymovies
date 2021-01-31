package com.dicoding.ohmymovies.ui.favoriteTvshows

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MyFragmentFactory
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteTvshowsFragmentTest {

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    /**
     * Memastikan recyclerview tvshow tidak tampil dan gambar empty tampil
     */
    @Test
    fun dataFavoriteTvshowsKosong() {
        val factory = MyFragmentFactory()
        launchFragmentInContainer<FavoriteTvshowsFragment>(
            factory = factory
        )
        Espresso.onView(ViewMatchers.withId(R.id.error))
            .check(ViewAssertions.matches(IsNot.not(isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.empty))
            .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.listFavoriteTvshow))
            .check(ViewAssertions.matches(IsNot.not(isDisplayed())))
    }

}