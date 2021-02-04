package com.dicoding.ohmymovies.detailtvshow

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
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.domain.usecase.TvshowUseCase
import com.ohmymovies.core.ui.args.DetailTvshowActivityArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailTvshowViewModel(
    application: Application,
    private val tvshowUseCase: TvshowUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : AndroidViewModel(application) {

    private val _tvshowResponse = MutableLiveData<TvShowModel>()
    val tvshowResponse: LiveData<TvShowModel> = _tvshowResponse

    private val _detailTvshowShow = MutableLiveData<Boolean>().apply { value = true }
    val detailTvshowShow: LiveData<Boolean> = _detailTvshowShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun onNavArgs(context: Context, data: DetailTvshowActivityArgs?) {
        if (data?.id != null && data.id!! > 0) {
            if (data.isOpenFromFavorite != null && data.isOpenFromFavorite!!) {
                getTvshowFavorite(data.id!!)
            } else {
                getTvshow(data.id!!)
            }
        } else {
            _detailTvshowShow.value = false
            _error.value = true
            _errorMessage.value = context.getString(R.string.tvshow_id_not_found)
        }
    }

    private fun getTvshowFavorite(id: Int) {
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when (val response = tvshowUseCase.getFavoriteTvshow(id)) {
                is Result.Success -> {
                    _detailTvshowShow.postValue(true)
                    _tvshowResponse.postValue(response.data)
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

    private fun getTvshow(id: Int) {
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            tvshowUseCase.getTvshow(id).collect {
                when (it) {
                    is Result.Success -> {
                        _detailTvshowShow.postValue(true)
                        _tvshowResponse.postValue(it.data)
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
        tvshowResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = tvshowUseCase.addFavoriteTvshow(it)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke()
                        }
                    }
                    is Result.Error -> {
                        Util.showToast(
                            getApplication(),
                            response.message
                        )
                    }
                }
            }
        }
    }

    fun removeFromFavorite(onSuccess: suspend () -> Unit) {
        tvshowResponse.value?.let {
            viewModelScope.launch(dispatcher) {
                when (val response = tvshowUseCase.removeFavoriteTvshow(it.id ?: 0)) {
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