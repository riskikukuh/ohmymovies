package com.dicoding.ohmymovies.ui.movies

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.ohmymovies.data.Event
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.Result.*
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.databinding.EmptyBinding
import com.dicoding.ohmymovies.databinding.FragmentMoviesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.util.EspressoIdlingResource

open class MoviesViewModel(
    application: Application,
    private val repository: MovieRepository,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<MovieModel>>()
    open val movies : LiveData<List<MovieModel>> = _movies

    private val _moviesShow = MutableLiveData<Boolean>()
    open val moviesShow : LiveData<Boolean> = _moviesShow

    val isMoviesEmpty = Transformations.map(_movies){
        it.isEmpty()
    }

    private val _emptyMessage = MutableLiveData<String>()
    open val emptyMessage : LiveData<String> = _emptyMessage

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    open val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    open val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    open val errorException : LiveData<Exception> = _errorException

    private val _refreshMoviesEvent = MutableLiveData<Event<Boolean>>()
    open val refreshMoviesEvent : LiveData<Event<Boolean>> = _refreshMoviesEvent

    init {
        fetchMovies(true)
    }

    fun fetchMovies(update: Boolean, isFromSwipe : Boolean = false) {
        _error.value = false
        _moviesShow.value = false
        if (!isFromSwipe) _loading.value = true
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when(val response = repository.getMovies(update, getApplication())){
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
            if (isFromSwipe){
                _refreshMoviesEvent.postValue(Event(false))
            }else{
                _loading.postValue(false)
            }
            EspressoIdlingResource.decrement()
        }
    }

}