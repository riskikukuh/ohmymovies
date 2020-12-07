package com.dicoding.ohmymovies.ui.favoriteTvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.espresso.IdlingRegistry
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.MainCoroutineRule
import com.dicoding.ohmymovies.util.Util
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteTvshowsViewModelTest {
    private var repository: MovieRepository = Mockito.mock(MovieRepository::class.java)

    private lateinit var viewModel: FavoriteTvshowsViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fakeTvshowPagedList: PagedList<TvshowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvshowsViewModel(repository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `fetchFavoriteTvshows success test`() {
        val tvshowsLive = MutableLiveData<Result<PagedList<TvshowEntity>>>()
        tvshowsLive.value = Result.Success(fakeTvshowPagedList)
        Mockito.`when`(repository.getFavoriteTvshows()).thenReturn(tvshowsLive)
        Truth.assertThat(viewModel.fetchFavoriteTvshows()).isEqualTo(tvshowsLive)
        Mockito.verify(repository).getFavoriteTvshows()
    }

    @Test
    fun `fetchFavoriteTvshows error test`() {
        val tvshowsLive = MutableLiveData<Result<PagedList<TvshowEntity>>>()
        tvshowsLive.value = Result.Error(Util.exception)
        Mockito.`when`(repository.getFavoriteTvshows()).thenReturn(tvshowsLive)
        Truth.assertThat(viewModel.fetchFavoriteTvshows()).isEqualTo(tvshowsLive)
        Mockito.verify(repository).getFavoriteTvshows()
    }

}