package com.ohmymovies.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.TvshowEntity
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class TvshowInteractor(private val repository: IMoviesRepository) : TvshowUseCase {
    override suspend fun getTvshows(): Flow<Result<List<TvShowModel>>> {
        return repository.getTvshows()
    }

    override suspend fun getTvshow(id: Int): Flow<Result<TvShowModel>> {
        return repository.getTvshow(id)
    }

    override fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>> {
        return repository.getFavoriteTvshows()
    }

    override suspend fun addFavoriteTvshow(data: TvShowModel): Result<Boolean> {
        return repository.addTvshowToFavorite(data)
    }

    override suspend fun removeFavoriteTvshow(id: Int): Result<Boolean> {
        return repository.removeFavoriteTvshow(id)
    }

    override suspend fun getFavoriteTvshow(id: Int): Result<TvShowModel> {
        return repository.getFavoriteTvshow(id)
    }
}