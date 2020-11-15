package com.dicoding.ohmymovies.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.Util.exception
import com.dicoding.ohmymovies.util.Util.fakeMovie
import com.dicoding.ohmymovies.util.Util.fakeTvShow
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DefaultMovieRepositoryTest {

    private var repository: MovieRepository = mock(MovieRepository::class.java)

    private val fakeMovies: List<MovieModel> = listOf(fakeMovie)

    private val fakeTvShows = listOf(fakeTvShow)

    @Mock
    private lateinit var mockContext: Context

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {}

    @Test
    fun `getMovies success test`() = runBlockingTest {
        `when`(repository.getMovies(true, mockContext)).thenReturn(Success(fakeMovies))
        assertThat(repository.getMovies(true, mockContext)).isEqualTo(Success(fakeMovies))
    }

    @Test
    fun `getMovies error test`() = runBlockingTest {
        `when`(repository.getMovies(true, mockContext)).thenReturn(Error(exception))
        assertThat(repository.getMovies(true, mockContext)).isEqualTo(Error(exception))
    }

    @Test
    fun `getTvShows success test`() = runBlockingTest {
        `when`(repository.getTvShows(true, mockContext)).thenReturn(Success(fakeTvShows))
        assertThat(repository.getTvShows(true, mockContext)).isEqualTo(Success(fakeTvShows))
    }

    @Test
    fun `getTvShows error test`() = runBlockingTest {
        `when`(repository.getTvShows(true, mockContext)).thenReturn(Error(exception))
        assertThat(repository.getTvShows(true, mockContext)).isEqualTo(Error(exception))
    }

}