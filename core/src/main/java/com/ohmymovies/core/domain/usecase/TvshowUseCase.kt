package com.ohmymovies.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.TvshowEntity
import com.ohmymovies.core.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface TvshowUseCase {
    suspend fun getTvshows(): Flow<Result<List<TvShowModel>>>
    suspend fun getTvshow(id: Int): Flow<Result<TvShowModel>>
    fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>>
    suspend fun addFavoriteTvshow(data: TvShowModel): Result<Boolean>
    suspend fun removeFavoriteTvshow(id: Int): Result<Boolean>
    suspend fun getFavoriteTvshow(id: Int): Result<TvShowModel>
}