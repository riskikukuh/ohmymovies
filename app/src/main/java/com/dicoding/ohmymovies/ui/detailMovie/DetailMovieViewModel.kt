package com.dicoding.ohmymovies.ui.detailMovie

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Util
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.domain.usecase.MovieUseCase
import com.ohmymovies.core.ui.args.DetailMovieActivityArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailMovieViewModel(
    application: Application,
    private val movieUseCase: MovieUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : AndroidViewModel(application) {

    private val _movieResponse = MutableLiveData<MovieModel>()
    val movieResponse: LiveData<MovieModel> = _movieResponse

    private val _detailMovieShow = MutableLiveData<Boolean>().apply { value = true }
    val detailMovieShow: LiveData<Boolean> = _detailMovieShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun onNavArgs(context: Context, data: DetailMovieActivityArgs?) {
        if (data?.id != null && data.id!! > 0) {
            if (data.isOpenFromFavorite != null && data.isOpenFromFavorite!!) {
                getFavoriteMovie(data.id!!)
            } else {
                getMovie(data.id!!)
            }
        } else {
            _detailMovieShow.value = false
            _error.value = true
            _errorMessage.value = context.getString(R.string.movie_id_not_found)
        }
    }

    private fun getFavoriteMovie(id: Int) {
        _loading.value = true
        _error.value = false
        _detailMovieShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = movieUseCase.getFavoriteMovie(id)) {
                is Result.Success -> {
                    _detailMovieShow.postValue(true)
                    _movieResponse.postValue(response.data)
                }
                is Result.Error -> {
                    _error.postValue(true)
                    _errorMessage.postValue(response.message)
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
            movieUseCase.getMovie(id).collect {
                when (it) {
                    is Result.Success -> {
                        _detailMovieShow.postValue(true)
                        _movieResponse.postValue(it.data)
                    }
                    is Result.Error -> {
                        _error.postValue(true)
                        _errorMessage.postValue(it.message)
                    }
                    is Result.Loading -> {
                        _loading.postValue(it.state)
                    }
                }
            }
            _loading.postValue(false)
            EspressoIdlingResource.decrement()
        }
    }

    fun addToFavorite(onSuccess: suspend () -> Unit) {
        movieResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = movieUseCase.addFavoriteMovie(it)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke()
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            Util.showToast(
                                getApplication(),
                                response.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun removeFromFavorite(onSuccess: suspend () -> Unit) {
        movieResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = movieUseCase.removeFavoriteMovie(it.id ?: 0)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke()
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            Util.showToast(
                                getApplication(),
                                response.message
                            )
                        }
                    }
                }
            }
        }
    }


}