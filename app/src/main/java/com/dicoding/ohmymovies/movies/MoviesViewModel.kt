package com.dicoding.ohmymovies.movies

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Event
import com.ohmymovies.core.data.Result.Error
import com.ohmymovies.core.data.Result.Success
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(
    application: Application,
    private val moviesUseCase: MovieUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies : LiveData<List<MovieModel>> = _movies

    private val _moviesShow = MutableLiveData<Boolean>()
    val moviesShow : LiveData<Boolean> = _moviesShow

    val isMoviesEmpty = Transformations.map(_movies){
        it.isEmpty()
    }

    private val _emptyMessage = MutableLiveData<String>()
    val emptyMessage: LiveData<String> = _emptyMessage

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _refreshMoviesEvent = MutableLiveData<Event<Boolean>>()
    val refreshMoviesEvent: LiveData<Event<Boolean>> = _refreshMoviesEvent

    init {
        fetchMovies()
    }

    fun fetchMovies(isFromSwipe: Boolean = false) {
        _error.value = false
        _moviesShow.value = false
        if (!isFromSwipe) _loading.value = true
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            moviesUseCase.getMovies().collect {
                when (it) {
                    is Success -> {
                        val movieShow = it.data.isNotEmpty()
                        _moviesShow.postValue(movieShow)
                        _movies.postValue(it.data)
                        if (!movieShow) {
                            _emptyMessage.postValue(getApplication<Application>().getString(R.string.movies_empty))
                        }
                    }
                    is Error -> {
                        _error.postValue(true)
                        _errorMessage.postValue(it.message)
                    }
                }
            }
            if (isFromSwipe) {
                _refreshMoviesEvent.postValue(Event(false))
            } else {
                _loading.postValue(false)
            }
            EspressoIdlingResource.decrement()
        }
    }

    fun checkFavoriteState(movieId: Int) {
        var isFavorite: Boolean
        viewModelScope.launch(dispatcher) {
            isFavorite = when (moviesUseCase.getFavoriteMovie(movieId)) {
                is Success -> {
                    true
                }
                is Error -> {
                    false
                }
                else -> {
                    false
                }
            }
            changeFavoriteState(movieId, isFavorite)
        }
    }

    private fun changeFavoriteState(id: Int, data: Boolean) {
        movies.value?.let {
            val temp = movies.value!!
            temp.firstOrNull { it.id == id }?.apply {
                isFavorite = data
            }
            _movies.postValue(temp)
        }
    }

}