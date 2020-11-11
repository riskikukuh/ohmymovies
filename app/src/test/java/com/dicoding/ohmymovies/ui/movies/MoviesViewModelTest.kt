package com.dicoding.ohmymovies.ui.movies

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Event
import com.dicoding.ohmymovies.data.Result.*
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    private var repository: MovieRepository = mock(MovieRepository::class.java)

    private val fakeMovies: List<MovieModel> = listOf(Util.fakeMovie)

    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var application: Application

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(application, repository, mainCoroutineRule.coroutineContext)
    }

    @Test
    fun `fetchMovies from swipe false movies is empty success test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Success(emptyList()))
        `when`(application.getString(R.string.movies_empty)).thenReturn("Movies empty")

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.refreshMoviesEvent)).isNull()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.movies)).isEmpty()
        assertThat(LiveDataTestUtil.getValue(viewModel.isMoviesEmpty)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.emptyMessage)).matches(application.getString(
            R.string.movies_empty))
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchMovies from swipe true movies is empty success test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Success(emptyList()))
        `when`(application.getString(R.string.movies_empty)).thenReturn("Empty")

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.movies)).isEmpty()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isMoviesEmpty)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.emptyMessage)).matches(application.getString(R.string.movies_empty))
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshMoviesEvent).peekContent()).isFalse()
    }

    @Test
    fun `fetchMovies from swipe false movies is not empty success test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Success(fakeMovies))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.movies)).isEqualTo(fakeMovies)
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isMoviesEmpty)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchMovies from swipe true movies is not empty success test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Success(fakeMovies))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.movies)).isEqualTo(fakeMovies)
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isMoviesEmpty)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshMoviesEvent).peekContent()).isFalse()
    }

    @Test
    fun `fetchMovies from swipe false error test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Error(Util.exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(Util.exception)
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchMovies from swipe true error test`() = runBlockingTest {
        `when`(repository.getMovies(false, application)).thenReturn(Error(Util.exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchMovies(false, isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(repository).getMovies(false, application)

        assertThat(LiveDataTestUtil.getValue(viewModel.moviesShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(Util.exception)
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshMoviesEvent).peekContent()).isFalse()
    }

}