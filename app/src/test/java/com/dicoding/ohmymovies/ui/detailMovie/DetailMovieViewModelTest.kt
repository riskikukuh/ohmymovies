package com.dicoding.ohmymovies.ui.detailMovie

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.ui.tvshows.TvshowsViewModel
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    private val fakeArgs: DetailMovieActivityArgs = DetailMovieActivityArgs(fakeTitle, fakeMovie)

    private lateinit var viewModel: DetailMovieViewModel

    @Mock
    private lateinit var application: Application

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel =
            DetailMovieViewModel(application, repository, mainCoroutineRule.coroutineContext)
    }

    @Test
    fun `onNavArgs movie null test`(){

        `when`(application.getString(R.string.movie_id_not_found)).thenReturn("Movie id not found")

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(fakeArgs.copy(movie = null))

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        verify(application).getString(R.string.movie_id_not_found)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException).message).isEqualTo(application.getString(R.string.movie_id_not_found))
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `onNavArgs movie not null test`(){

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(fakeArgs)

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.detailMovieShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.movie)).isEqualTo(fakeMovie)
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }
}