package com.dicoding.ohmymovies.ui.detailTvshow

import android.content.Context
import android.content.Intent
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.ui.detailMovie.DetailMovieActivity
import com.dicoding.ohmymovies.util.EspressoExt
import com.dicoding.ohmymovies.util.EspressoExt.matchToolbarTitle
import com.dicoding.ohmymovies.util.EspressoExt.withDrawable
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.FakeData
import com.dicoding.ohmymovies.util.FakeData.TV_SHOW
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailTvshowActivityTest {

    private lateinit var context: Context
    private lateinit var listGenre: RecyclerView

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_argsMovieIsThere() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailTvshowActivity::class.java
        ).apply {
            putExtra(DetailTvshowActivity.ARGS, DetailTvshowActivityArgs(TV_SHOW.name, TV_SHOW))
        }
        ActivityScenario.launch<DetailTvshowActivity>(intent).onActivity {
            listGenre = it.genres
        }
        onView(withId(R.id.error))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.toolbar))
            .check(matchToolbarTitle(TV_SHOW.name))
        onView(withId(R.id.posterTvshow))
            .check(matches(withDrawable(R.drawable.poster_naruto_shipudden)))
        onView(withId(R.id.rating))
            .check(matches(withText(TV_SHOW.getRating())))
        onView(withId(R.id.status))
            .check(matches(withText(TV_SHOW.status)))
        onView(withId(R.id.genres))
            .perform(RecyclerViewActions.scrollToPosition<GenresAdapter.ViewHolder>(listGenre.size))
        onView(withId(R.id.overview))
            .check(matches(withText(TV_SHOW.overview)))
        onView(withId(R.id.homepage))
            .check(matches(withText(TV_SHOW.homepage)))
        onView(withId(R.id.language))
            .check(matches(withText(TV_SHOW.getLanguages())))
        onView(withId(R.id.episodeCount))
            .check(matches(withText(TV_SHOW.episodeCountAsString())))
    }

    @Test
    fun test_argsMovieErrorOrNull() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailTvshowActivity::class.java
        ).apply {
            putExtra(DetailTvshowActivity.ARGS, DetailTvshowActivityArgs("", null))
        }
        ActivityScenario.launch<DetailTvshowActivity>(intent).onActivity {
            listGenre = it.genres
        }
        onView(withId(R.id.toolbar)).check(matchToolbarTitle(""))
        onView(withId(R.id.contentDetailTvshow))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.error))
            .check(matches(isDisplayed()))
        onView(withId(R.id.errorMsg))
            .check(matches(withText(context.getString(R.string.tvshow_id_not_found))))
    }

}