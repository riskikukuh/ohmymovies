package com.dicoding.ohmymovies.ui.tvshows

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Event
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TvshowsViewModel(
    application: Application,
    private val movieRepository: MovieRepository,
    private val dispatcher : CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application){

    private val _tvshows = MutableLiveData<List<TvShowModel>>()
    val tvshows : LiveData<List<TvShowModel>> = _tvshows

    private val _tvshowsShow = MutableLiveData<Boolean>()
    val tvshowsShow : LiveData<Boolean> = _tvshowsShow

    val isTvshowsEmpty = Transformations.map(_tvshows){
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

    private val _refreshTvshowsEvent = MutableLiveData<Event<Boolean>>()
    val refreshTvshowsEvent : LiveData<Event<Boolean>> = _refreshTvshowsEvent

    init {
        fetchTvshows()
    }

    fun fetchTvshows(isFromSwipe : Boolean = false) {
        _error.value = false
        _tvshowsShow.value = false
        if (!isFromSwipe) _loading.value = true
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when(val response = movieRepository.getTvShows()){
                is Result.Success -> {
                    val isNotEmpty = response.data.isNotEmpty()
                    _tvshowsShow.postValue(isNotEmpty)
                    _tvshows.postValue(response.data)
                    if (!isNotEmpty){
                        _emptyMessage.postValue(getApplication<Application>().getString(R.string.tvshows_empty))
                    }
                }
                is Result.Error -> {
                    _error.postValue(true)
                    _errorException.postValue(response.exception)
                }
            }
            if (isFromSwipe) {
                _refreshTvshowsEvent.postValue(Event(false))
            } else {
                _loading.postValue(false)
            }
            EspressoIdlingResource.decrement()
        }
    }

    fun checkFavoriteState(tvshowId: Int) {
        var isFavorite: Boolean
        viewModelScope.launch(dispatcher) {
            isFavorite = when (val response = movieRepository.getFavoriteTvshow(tvshowId)) {
                is Result.Success -> {
                    true
                }
                is Result.Error -> {
                    false
                }
            }
            changeFavoriteState(tvshowId, isFavorite)
        }
    }

    private fun changeFavoriteState(id: Int, data: Boolean) {
        tvshows.value?.let {
            val temp = tvshows.value!!
            temp.firstOrNull { it.id == id }?.apply {
                isFavorite = data
            }
            _tvshows.postValue(temp)
        }
    }


}