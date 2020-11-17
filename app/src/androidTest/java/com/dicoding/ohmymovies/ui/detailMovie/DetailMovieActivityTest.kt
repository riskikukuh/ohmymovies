package com.dicoding.ohmymovies.ui.detailMovie

import android.content.Intent
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.util.EspressoExt.matchToolbarTitle
import com.dicoding.ohmymovies.util.EspressoExt.withDrawable
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.FakeData.MOVIE
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailMovieActivityTest {

    private lateinit var listGenre : RecyclerView

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Menampilkan data movie saat diberikan data yang tidak null
     */
    @Test
    fun loadMovie() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.ARGS, DetailMovieActivityArgs(MOVIE.title, MOVIE))
        }
        ActivityScenario.launch<DetailMovieActivity>(intent).onActivity {
            listGenre = it.genres
        }
        onView(withId(R.id.error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.toolbar)).check(matchToolbarTitle(MOVIE.title))
        onView(withId(R.id.posterMovie)).check(matches(withDrawable(R.drawable.poster_alita)))
        onView(withId(R.id.rating)).check(matches(withText(MOVIE.getRating())))
        onView(withId(R.id.adult)).check(matches(withText(MOVIE.getAdult())))
        onView(withId(R.id.genres)).perform(RecyclerViewActions.scrollToPosition<GenresAdapter.ViewHolder>(listGenre.size))
        onView(withId(R.id.overview)).check(matches(withText(MOVIE.overview)))
        onView(withId(R.id.homepage)).check(matches(withText(MOVIE.homepage)))
        onView(withId(R.id.spokenLanguage)).check(matches(withText(MOVIE.getSpokenLanguageAsString())))
        onView(withId(R.id.releaseDate)).check(matches(withText(MOVIE.releaseDate)))
    }

    /**
     * Menampilkan error saat diberikan data null
     */
    @Test
    fun loadMovieError(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailMovieActivity::class.java).apply{
            putExtra(DetailMovieActivity.ARGS, DetailMovieActivityArgs("", null))
        }
        val scenario = ActivityScenario.launch<DetailMovieActivity>(intent).onActivity {
            listGenre = it.genres
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withId(R.id.toolbar)).check(matchToolbarTitle(""))
        onView(withId(R.id.detailMovie)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error)).check(matches(isDisplayed()))
        onView(withId(R.id.errorMsg)).check(matches(withText(context.getString(R.string.movie_id_not_found))))
    }

}