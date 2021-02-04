package com.ohmymovies.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.MovieEntity
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val moviesRepository: IMoviesRepository) : MovieUseCase {
    override suspend fun getMovies(): Flow<Result<List<MovieModel>>> = moviesRepository.getMovies()

    override suspend fun getMovie(id: Int): Flow<Result<MovieModel>> {
        return moviesRepository.getMovie(id)
    }

    override suspend fun addFavoriteMovie(data: MovieModel): Result<Boolean> {
        return moviesRepository.addMovieToFavorite(data)
    }

    override suspend fun removeFavoriteMovie(id: Int): Result<Boolean> {
        return moviesRepository.removeFavoriteMovie(id)
    }

    override suspend fun getFavoriteMovie(id: Int): Result<MovieModel> {
        return moviesRepository.getFavoriteMovie(id)
    }

    override fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>> {
        return moviesRepository.getFavoriteMovies()
    }

}