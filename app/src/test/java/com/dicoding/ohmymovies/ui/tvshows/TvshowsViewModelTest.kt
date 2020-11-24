package com.dicoding.ohmymovies.ui.tvshows

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth.*
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
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvshowsViewModelTest {
    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private val fakeTvshows: List<TvShowModel> = listOf(Util.fakeTvShow)

    private lateinit var viewModel: TvshowsViewModel

    @Mock
    private lateinit var application: Application

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvshowsViewModel(application, repository, mainCoroutineRule.coroutineContext)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `fetchTvshows from swipe false tvshows is empty success test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Success(emptyList()))
        `when`(application.getString(R.string.tvshows_empty)).thenReturn("Tvshows Empty")

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.refreshTvshowsEvent)).isNull()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshows)).isEmpty()
        assertThat(LiveDataTestUtil.getValue(viewModel.isTvshowsEmpty)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.emptyMessage)).matches(application.getString(R.string.tvshows_empty))
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchTvshows from swipe true movies is empty success test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Success(emptyList()))
        `when`(application.getString(R.string.tvshows_empty)).thenReturn("Tvshows Empty")

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshows)).isEmpty()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isTvshowsEmpty)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.emptyMessage)).matches(application.getString(
            R.string.tvshows_empty))
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshTvshowsEvent).peekContent()).isFalse()
    }

    @Test
    fun `fetchTvshows from swipe false movies is not empty success test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Success(fakeTvshows))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshows)).isEqualTo(fakeTvshows)
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isTvshowsEmpty)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchTvshows from swipe true movies is not empty success test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Success(fakeTvshows))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshows)).isEqualTo(fakeTvshows)
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.isTvshowsEmpty)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshTvshowsEvent).peekContent()).isFalse()
    }

    @Test
    fun `fetchTvshows from swipe false error test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Error(Util.exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = false)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(Util.exception)
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `fetchTvshows from swipe true error test`() = runBlockingTest {
        `when`(repository.getTvShows())
            .thenReturn(Result.Error(Util.exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.fetchTvshows(isFromSwipe = true)

        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()

        Mockito.verify(repository).getTvShows()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.tvshowsShow)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(Util.exception)
        assertThat(LiveDataTestUtil.getValue(viewModel.refreshTvshowsEvent).peekContent()).isFalse()
    }

}