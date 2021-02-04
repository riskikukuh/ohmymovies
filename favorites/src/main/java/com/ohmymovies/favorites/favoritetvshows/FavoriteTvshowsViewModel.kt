package com.ohmymovies.favorites.favoritetvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.TvshowEntity
import com.ohmymovies.core.domain.usecase.TvshowUseCase
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class FavoriteTvshowsViewModel(
    private val tvshowUseCase: TvshowUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ViewModel() {
    fun fetchFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>> =
        tvshowUseCase.getFavoriteTvshows()
}