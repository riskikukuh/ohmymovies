package com.dicoding.ohmymovies.ui.detailMovie

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailMovieViewModel(
    application: Application,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val repository: MovieRepository,
) : AndroidViewModel(application) {

    private val _movieResponse = MutableLiveData<MovieModel>()
    val movieResponse: LiveData<MovieModel> = _movieResponse

    private val _detailMovieShow = MutableLiveData<Boolean>().apply { value = true }
    val detailMovieShow: LiveData<Boolean> = _detailMovieShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException: LiveData<Exception> = _errorException

    fun onNavArgs(context : Context, data: DetailMovieActivityArgs?) {
        if (data?.id != null && data.id > 0) {
            if (data.isOpenFromFavorite != null && data.isOpenFromFavorite) {
                getFavoriteMovie(data.id)
            } else {
                getMovie(data.id)
            }
        } else {
            _detailMovieShow.value = false
            _error.value = true
            _errorException.value = Exception(context.getString(R.string.movie_id_not_found))
        }
    }

    private fun getFavoriteMovie(id: Int) {
        _loading.value = true
        _error.value = false
        _detailMovieShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = repository.getFavoriteMovie(id)) {
                is Result.Success -> {
                    _detailMovieShow.postValue(true)
                    _movieResponse.postValue(MovieModel.fromMovieEntityWithGenre(response.data))
                }
                is Result.Error -> {
                    _error.postValue(true)
                    _errorException.postValue(response.exception)
                }
            }
            _loading.postValue(false)
            EspressoIdlingResource.decrement()
        }
    }

    private fun getMovie(id: Int) {
        _loading.value = true
        _error.value = false
        _detailMovieShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = repository.getMovie(id)) {
                is Result.Success -> {
                    _detailMovieShow.postValue(true)
                    _movieResponse.postValue(response.data)
                }
                is Result.Error -> {
                    _error.postValue(true)
                    _errorException.postValue(response.exception)
                }
            }
            _loading.postValue(false)
            EspressoIdlingResource.decrement()
        }
    }

    fun addToFavorite(onSuccess: suspend () -> Unit) {
        movieResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = repository.addMovieToFavorite(it)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke()
                        }
                    }
                    is Result.Error -> {
                        Util.showToast(
                            getApplication(),
                            response.exception.message ?: "Unknown error occured"
                        )
                    }
                }
            }
        }
    }

    fun removeFromFavorite(onSuccess: suspend () -> Unit) {
        movieResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = repository.removeMovieFromFavorite(it.id ?: 0)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke()
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            Util.showToast(
                                getApplication(),
                                response.exception.message ?: "Unknown error occurred"
                            )
                        }
                    }
                }
            }
        }
    }


}