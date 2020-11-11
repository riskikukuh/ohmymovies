package com.dicoding.ohmymovies.ui.detailTvshow

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.LiveDataTestUtil
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailTvshowViewModelTest {

    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private val fakeTvshow: TvShowModel = Util.fakeTvShow

    private val fakeTitle = "fake title"

    private val fakeArgs: DetailTvshowActivityArgs = DetailTvshowActivityArgs(fakeTitle, fakeTvshow)

    private lateinit var viewModel: DetailTvshowViewModel

    @Mock
    private lateinit var application: Application

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel =
            DetailTvshowViewModel(application, repository, mainCoroutineRule.coroutineContext)
    }

    @Test
    fun `onNavArgs tvshow null test`(){

        Mockito.`when`(application.getString(R.string.tvshow_id_not_found)).thenReturn("Movie id not found")

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(fakeArgs.copy(tvshow = null))

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        Mockito.verify(application).getString(R.string.tvshow_id_not_found)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.errorException).message)
            .isEqualTo(application.getString(R.string.tvshow_id_not_found))
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }

    @Test
    fun `onNavArgs tvshow not null test`(){

        mainCoroutineRule.pauseDispatcher()

        viewModel.onNavArgs(fakeArgs)

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.error)).isFalse()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isFalse()

        mainCoroutineRule.resumeDispatcher()

        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.detailTvshowShow)).isTrue()
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.tvshow)).isEqualTo(fakeTvshow)
        Truth.assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
    }
}