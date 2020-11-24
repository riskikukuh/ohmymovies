package com.dicoding.ohmymovies.ui.detailTvshow

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth
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
class DetailTvshowViewModelTest {

    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private val fakeTvshow: TvShowModel = Util.fakeTvShow

    private val fakeTitle = "fake title"

    private val fakeArgs: DetailTvshowActivityArgs = DetailTvshowActivityArgs(1, fakeTitle)

    private lateinit var viewModel: DetailTvshowViewModel

    @Mock
    private lateinit var context : Context

    @Mock
    private lateinit var exception : Exception

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel =
            DetailTvshowViewModel(mainCoroutineRule.coroutineContext, repository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `onNavArgs error id null test`() = runBlockingTest{

        `when`(context.getString(R.string.tvshow_id_not_found)).thenReturn("Tvshow id not found")

        viewModel.onNavArgs(context, null)

        verify(context).getString(R.string.tvshow_id_not_found)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorException).message).isEqualTo(context.getString(R.string.tvshow_id_not_found))
    }

    @Test
    fun `onNavArgs success and fetch tvshow success test`() = runBlockingTest{

        `when`(repository.getTvshow(1)).thenReturn(Result.Success(fakeTvshow))

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(context, fakeArgs)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        verify(repository).getTvshow(1)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.tvshowResponse)).isEqualTo(fakeTvshow)
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `onNavArgs success and fetch tvshow error test`() = runBlockingTest{

        `when`(repository.getTvshow(1)).thenReturn(Result.Error(exception))

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(context, fakeArgs)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        verify(repository).getTvshow(1)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorException)).isEqualTo(exception)
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }
}