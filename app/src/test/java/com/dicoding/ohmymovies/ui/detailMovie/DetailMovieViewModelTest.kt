package com.dicoding.ohmymovies.ui.detailMovie

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private val fakeMovie: MovieModel = Util.fakeMovie

    private val fakeTitle = "fake title"

    private val fakeArgs: DetailMovieActivityArgs = DetailMovieActivityArgs(1, fakeTitle)

    private lateinit var viewModel: DetailMovieViewModel

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var exception: Exception

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel =
            DetailMovieViewModel(application, mainCoroutineRule.coroutineContext, repository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `onNavArgs error id null test`() {

        `when`(context.getString(R.string.movie_id_not_found)).thenReturn("Movie id not found")

        viewModel.onNavArgs(context, null)

        verify(context).getString(R.string.movie_id_not_found)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException).message).isEqualTo(
            context.getString(
                R.string.movie_id_not_found
            )
        )
    }

    @Test
    fun `onNavArgs success and fetch movie success test`() = runBlockingTest {

        `when`(repository.getMovie(1)).thenReturn(Result.Success(fakeMovie))

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(context, fakeArgs)

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.movieResponse)).isEqualTo(fakeMovie)
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `onNavArgs success and fetch movie error test`() = runBlockingTest {

        `when`(repository.getMovie(1)).thenReturn(Result.Error(exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(context, fakeArgs)

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(exception)
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }
}