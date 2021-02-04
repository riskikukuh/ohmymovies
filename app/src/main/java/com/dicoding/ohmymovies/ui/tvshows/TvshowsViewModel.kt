package com.dicoding.ohmymovies.ui.tvshows

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Event
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.domain.usecase.TvshowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TvshowsViewModel(
    application: Application,
    private val tvshowUseCase: TvshowUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application){

    private val _tvshows = MutableLiveData<List<TvShowModel>>()
    val tvshows : LiveData<List<TvShowModel>> = _tvshows

    private val _tvshowsShow = MutableLiveData<Boolean>()
    val tvshowsShow : LiveData<Boolean> = _tvshowsShow

    val isTvshowsEmpty = Transformations.map(_tvshows){
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

    private val _refreshTvshowsEvent = MutableLiveData<Event<Boolean>>()
    val refreshTvshowsEvent: LiveData<Event<Boolean>> = _refreshTvshowsEvent

    init {
        fetchTvshows()
    }

    fun fetchTvshows(isFromSwipe: Boolean = false) {
        _error.value = false
        _tvshowsShow.value = false
        if (!isFromSwipe) _loading.value = true
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            tvshowUseCase.getTvshows().collect {
                when (it) {
                    is Result.Success -> {
                        val isNotEmpty = it.data.isNotEmpty()
                        _tvshowsShow.postValue(isNotEmpty)
                        _tvshows.postValue(it.data)
                        if (!isNotEmpty) {
                            _emptyMessage.postValue(getApplication<Application>().getString(R.string.tvshows_empty))
                        }
                    }
                    is Result.Error -> {
                        _error.postValue(true)
                        _errorMessage.postValue(it.message)
                    }
                    else -> {
                    }
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
            isFavorite = when (tvshowUseCase.getFavoriteTvshow(tvshowId)) {
                is Result.Success -> {
                    true
                }
                is Result.Error -> {
                    false
                }
                else -> {
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