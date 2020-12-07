package com.dicoding.ohmymovies.ui.favoriteMovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteMoviesViewModelTest {
    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private lateinit var viewModel: FavoriteMoviesViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fakeMoviePagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMoviesViewModel(repository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `fetchFavoriteMovies success test`() {
        val moviesLive = MutableLiveData<Result<PagedList<MovieEntity>>>()
        moviesLive.value = Result.Success(fakeMoviePagedList)
        `when`(repository.getFavoriteMovies()).thenReturn(moviesLive)
        assertThat(viewModel.fetchFavoriteMovies()).isEqualTo(moviesLive)
        verify(repository).getFavoriteMovies()
    }

    @Test
    fun `fetchFavoriteMovies error test`() {
        val moviesLive = MutableLiveData<Result<PagedList<MovieEntity>>>()
        moviesLive.value = Result.Error(Util.exception)
        `when`(repository.getFavoriteMovies()).thenReturn(moviesLive)
        assertThat(viewModel.fetchFavoriteMovies()).isEqualTo(moviesLive)
        verify(repository).getFavoriteMovies()
    }

}