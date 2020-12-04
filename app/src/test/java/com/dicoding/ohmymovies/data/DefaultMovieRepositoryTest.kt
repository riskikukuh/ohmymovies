package com.dicoding.ohmymovies.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Util.exception
import com.dicoding.ohmymovies.util.Util.fakeMovie
import com.dicoding.ohmymovies.util.Util.fakeMovieEntity
import com.dicoding.ohmymovies.util.Util.fakeTvShow
import com.dicoding.ohmymovies.util.Util.fakeTvshowEntity
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
    private lateinit var context : Context

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
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
    fun `getFavoriteMovies success test`() = runBlockingTest {
        `when`(repository.getFavoriteMovies()).thenReturn(Success(listOf(fakeMovieEntity)))
        assertThat(repository.getFavoriteMovies()).isEqualTo((Success(listOf(fakeMovies))))
        verify(repository).getFavoriteMovies()
    }

    @Test
    fun `getFavoriteMovies error test`() = runBlockingTest {
        `when`(repository.getFavoriteMovies()).thenReturn(Error(exception))
        assertThat(repository.getFavoriteMovies()).isEqualTo((Error(exception)))
        verify(repository).getFavoriteMovies()
    }

    @Test
    fun `getFavoriteTvshows success test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshows()).thenReturn(Success(listOf(fakeTvshowEntity)))
        assertThat(repository.getFavoriteTvshows()).isEqualTo((Success(listOf(fakeTvshowEntity))))
        verify(repository).getFavoriteTvshows()
    }

    @Test
    fun `getFavoriteTvshows error test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshows()).thenReturn(Error(exception))
        assertThat(repository.getFavoriteTvshows()).isEqualTo(Error(exception))
        verify(repository).getFavoriteTvshows()
    }

    @Test
    fun `getFavoriteMovie success test`() = runBlockingTest {
        `when`(repository.getFavoriteMovie(context, 1)).thenReturn(Success(fakeMovieEntity))
        assertThat(repository.getFavoriteMovie(context, 1)).isEqualTo(Success(fakeMovieEntity))
        verify(repository).getFavoriteMovie(context, 1)
    }

    @Test
    fun `getFavoriteMovie error test`() = runBlockingTest {
        `when`(repository.getFavoriteMovie(context, 0)).thenReturn(Error(exception))
        assertThat(repository.getFavoriteMovie(context, 0)).isEqualTo(Error(exception))
        verify(repository).getFavoriteMovie(context, 0)
    }

    @Test
    fun `getFavoriteTvshow success test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshow(context, 1)).thenReturn(Success(fakeTvshowEntity))
        assertThat(repository.getFavoriteTvshow(context, 1)).isEqualTo(Success(fakeTvshowEntity))
        verify(repository).getFavoriteTvshow(context, 1)
    }

    @Test
    fun `getFavoriteTvshow error test`() = runBlockingTest {
        `when`(repository.getFavoriteTvshow(context, 0)).thenReturn(Error(exception))
        assertThat(repository.getFavoriteTvshow(context, 0)).isEqualTo(Error(exception))
        verify(repository).getFavoriteTvshow(context, 0)
    }

}