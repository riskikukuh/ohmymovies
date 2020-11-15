package com.dicoding.ohmymovies.ui.detailTvshow

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.data.model.TvShowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs

class DetailTvshowViewModel(
    application: Application,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application){

    private val _argsTvshow = MutableLiveData<TvShowModel>()
    val tvshow: LiveData<TvShowModel> = _argsTvshow

    private val _detailTvshowShow = MutableLiveData<Boolean>().apply { value = true }
    val detailTvshowShow : LiveData<Boolean> = _detailTvshowShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading : LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException : LiveData<Exception> = _errorException

    fun onNavArgs(data: DetailTvshowActivityArgs?){
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        viewModelScope.launch(dispatcher) {
            if (data?.tvshow != null){
                _detailTvshowShow.postValue(true)
                data.tvshow.apply {
                    homepage = if (homepage == null || homepage?.isEmpty()!!) getApplication<Application>().getString(R.string.unknown) else homepage
                }
                _argsTvshow.postValue(data.tvshow)
            }else{
                _error.postValue(true)
                _errorException.postValue(Exception(getApplication<Application>().getString(R.string.tvshow_id_not_found)))
            }
            _loading.postValue(false)
        }
    }

}