package com.dicoding.ohmymovies.ui.movies

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Event
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(
    application: Application,
    private val repository: MovieRepository,
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
    val emptyMessage : LiveData<String> = _emptyMessage

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException : LiveData<Exception> = _errorException

    private val _refreshMoviesEvent = MutableLiveData<Event<Boolean>>()
    val refreshMoviesEvent : LiveData<Event<Boolean>> = _refreshMoviesEvent

    init {
        fetchMovies()
    }

    fun fetchMovies(isFromSwipe : Boolean = false) {
        _error.value = false
        _moviesShow.value = false
        if (!isFromSwipe) _loading.value = true
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when(val response = repository.getMovies()){
                is Success -> {
                    val movieShow = response.data.isNotEmpty()
                    _moviesShow.postValue(movieShow)
                    _movies.postValue(response.data)
                    if (!movieShow){
                        _emptyMessage.postValue(getApplication<Application>().getString(R.string.movies_empty))
                    }
                }
                is Error -> {
                    _error.postValue(true)
                    _errorException.postValue(response.exception)
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
        var isFavorite = false
        viewModelScope.launch(dispatcher) {
            isFavorite = when (val response = repository.getFavoriteMovie(movieId)) {
                is Success -> {
                    true
                }
                is Error -> {
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