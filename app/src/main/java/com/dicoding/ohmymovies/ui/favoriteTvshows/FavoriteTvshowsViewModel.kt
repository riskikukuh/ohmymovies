package com.dicoding.ohmymovies.ui.favoriteTvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.source.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class FavoriteTvshowsViewModel(
    private val repository: MovieRepository,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ViewModel() {
    fun fetchFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>> =
        repository.getFavoriteTvshows()
}