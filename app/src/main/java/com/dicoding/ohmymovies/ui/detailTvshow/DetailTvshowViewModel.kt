package com.dicoding.ohmymovies.ui.detailTvshow

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import com.dicoding.ohmymovies.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailTvshowViewModel(
    application: Application,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val repository: MovieRepository,
) : AndroidViewModel(application) {

    private val _tvshowResponse = MutableLiveData<TvShowModel>()
    val tvshowResponse: LiveData<TvShowModel> = _tvshowResponse

    private val _detailTvshowShow = MutableLiveData<Boolean>().apply { value = true }
    val detailTvshowShow: LiveData<Boolean> = _detailTvshowShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException : LiveData<Exception> = _errorException

    fun onNavArgs(context: Context, data: DetailTvshowActivityArgs?){
        if (data?.id != null && data.id > 0) {
            if (data.isOpenFromFavorite != null && data.isOpenFromFavorite) {
                getTvshowFavorite(data.id)
            } else {
                getTvshow(data.id)
            }
        } else {
            _detailTvshowShow.value = false
            _error.value = true
            _errorException.value = Exception(context.getString(R.string.tvshow_id_not_found))
        }
    }

    private fun getTvshowFavorite(id: Int) {
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = repository.getFavoriteTvshow(id)) {
                is Result.Success -> {
                    _detailTvshowShow.postValue(true)
                    _tvshowResponse.postValue(TvShowModel.fromTvshowEntityWithGenre(response.data))
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

    private fun getTvshow(id: Int) {
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = repository.getTvshow(id)) {
                is Result.Success -> {
                    _detailTvshowShow.postValue(true)
                    _tvshowResponse.postValue(response.data)
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
        tvshowResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = repository.addTvshowToFavorite(it)) {
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
        tvshowResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = repository.removeTvshowFromFavorite(it.id ?: 0)) {
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