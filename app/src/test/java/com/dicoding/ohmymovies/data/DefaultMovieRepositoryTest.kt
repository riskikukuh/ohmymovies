package com.dicoding.ohmymovies.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Util.exception
import com.dicoding.ohmymovies.util.Util.fakeMovie
import com.dicoding.ohmymovies.util.Util.fakeMovieWithGenreLanguage
import com.dicoding.ohmymovies.util.Util.fakeTvShow
import com.dicoding.ohmymovies.util.Util.fakeTvshowWithGenreLanguage
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DefaultMovieRepositoryTest {

    private var repository: MovieRepository = mock(MovieRepository::class.java)

    private val fakeMovies: List<MovieModel> = listOf(fakeMovie)

    private val fakeTvShows = listOf(fakeTvShow)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var packedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var packedListTvshow: PagedList<TvshowEntity>

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `getMovies success test`() = runBlockingTest {
        `when`(repository.getMovies()).thenReturn(Success(fakeMovies))
        assertThat(repository.getMovies()).isEqualTo(Success(fakeMovies))
        verify(repository).getMovies()
    }

    @Test
    fun `getMovies error test`() = runBlockingTest {
        `when`(repository.getMovies()).thenReturn(Error(exception))
        assertThat(repository.getMovies()).isEqualTo(Error(exception))
        verify(repository).getMovies()
    }

    @Test
    fun `getMovie success test`() = runBlockingTest {
        `when`(repository.getMovie(0)).thenReturn(Success(fakeMovie))
        assertThat(repository.getMovie(0)).isEqualTo(Success(fakeMovie))
        verify(repository).getMovie(0)
    }

    @Test
    fun `getMovie error test`() = runBlockingTest {
        `when`(repository.getMovie(0)).thenReturn(Error(exception))
        assertThat(repository.getMovie(0)).isEqualTo(Error(exception))
        verify(repository).getMovie(0)
    }

    @Test
    fun `getTvShows success test`() = runBlockingTest {
        `when`(repository.getTvShows()).thenReturn(Success(fakeTvShows))
        assertThat(repository.getTvShows()).isEqualTo(Success(fakeTvShows))
        verify(repository).getTvShows()
    }

    @Test
    fun `getTvShows error test`() = runBlockingTest {
        `when`(repository.getTvShows()).thenReturn(Error(exception))
        assertThat(repository.getTvShows()).isEqualTo(Error(exception))
        verify(repository).getTvShows()
    }

    @Test
    fun `getTvshow success test`() = runBlockingTest {
        `when`(repository.getTvshow(0)).thenReturn(Success(fakeTvShow))
        assertThat(repository.getTvshow(0)).isEqualTo(Success(fakeTvShow))
        verify(repository).getTvshow(0)
    }

    @Test
    fun `getTvshow error test`() = runBlockingTest {
        `when`(repository.getTvshow(0)).thenReturn(Error(exception))
        assertThat(repository.getTvshow(0)).isEqualTo(Error(exception))
        verify(repository).getTvshow(0)
    }

    @Test
    fun `getFavoriteMovies success test`() {
        val favoriteMovies = MutableLiveData<Result<PagedList<MovieEntity>>>()
        favoriteMovies.value = Success(packedListMovie)
        `when`(repository.getFavoriteMovies()).thenReturn(favoriteMovies)
        assertThat(repository.getFavoriteMovies()).isEqualTo(favoriteMovies)
        verify(repository).getFavoriteMovies()
    }

    @Test
    fun `getFavoriteMovies error test`() {
        val favoriteMovies = MutableLiveData<Result<PagedList<MovieEntity>>>()
        favoriteMovies.value = Error(exception)
        `when`(repository.getFavoriteMovies()).thenReturn(favoriteMovies)
        assertThat(repository.getFavoriteMovies()).isEqualTo(favoriteMovies)
        verify(repository).getFavoriteMovies()
    }

    @Test
    fun `getFavoriteTvshows success test`() {
        val favoriteTvshows = MutableLiveData<Result<PagedList<TvshowEntity>>>()
        favoriteTvshows.value = Success(packedListTvshow)
        `when`(repository.getFavoriteTvshows()).thenReturn(favoriteTvshows)
        assertThat(repository.getFavoriteTvshows()).isEqualTo(favoriteTvshows)
        verify(repository).getFavoriteTvshows()
    }

    @Test
    fun `getFavoriteTvshows error test`() {
        val favoriteTvshows = MutableLiveData<Result<PagedList<TvshowEntity>>>()
        favoriteTvshows.value = Error(exception)
        `when`(repository.getFavoriteTvshows()).thenReturn(favoriteTvshows)
        assertThat(repository.getFavoriteTvshows()).isEqualTo(favoriteTvshows)
        verify(repository).getFavoriteTvshows()
    }

    @Test
    fun `getFavoriteMovie success test`() = runBlockingTest {
        `when`(repository.getFavoriteMovie(1)).thenReturn(Success(fakeMovieWithGenreLanguage))
        assertThat(repository.getFavoriteMovie(1)).isEqualTo(Success(fakeMovieWithGenreLanguage))
        verify(repository).getFavoriteMovie(1)
    }

    @Test
    fun `getFavoriteMovie error test`() = runBlockingTest {
        `when`(repository.getFavoriteMovie(0)).thenReturn(Error(exception))
        assertThat(repository.getFavoriteMovie(0)).isEqualTo(Error(exception))
        verify(repository).getFavoriteMovie(0)
    }

    @Test
    fun `getFavoriteTvshow success test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshow(1)).thenReturn(Success(fakeTvshowWithGenreLanguage))
        assertThat(repository.getFavoriteTvshow(1)).isEqualTo(Success(fakeTvshowWithGenreLanguage))
        verify(repository).getFavoriteTvshow(1)
    }

    @Test
    fun `getFavoriteTvshow error test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshow(0)).thenReturn(Error(exception))
        assertThat(repository.getFavoriteTvshow(0)).isEqualTo(Error(exception))
        verify(repository).getFavoriteTvshow(0)
    }

}