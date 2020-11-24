package com.dicoding.ohmymovies.ui.detailTvshow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTvshowViewModel(
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val repository: MovieRepository,
) : ViewModel(){

    private val _tvshowResponse = MutableLiveData<TvShowModel>()
    val tvshowResponse: LiveData<TvShowModel> = _tvshowResponse

    private val _detailTvshowShow = MutableLiveData<Boolean>().apply { value = true }
    val detailTvshowShow : LiveData<Boolean> = _detailTvshowShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading : LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException : LiveData<Exception> = _errorException

    fun onNavArgs(context: Context, data: DetailTvshowActivityArgs?){
        if (data?.id != null && data.id > 0){
            getTvshow(data.id)
        }else{
            _detailTvshowShow.value = false
            _error.value = true
            _errorException.value = Exception(context.getString(R.string.tvshow_id_not_found))
        }
    }

    private fun getTvshow(id : Int) {
        _loading.value = true
        _error.value = false
        _detailTvshowShow.value = false
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            when(val response = repository.getTvshow(id)){
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

}