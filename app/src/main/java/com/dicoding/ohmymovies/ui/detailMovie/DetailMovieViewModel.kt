package com.dicoding.ohmymovies.ui.detailMovie

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.util.EspressoIdlingResource

class DetailMovieViewModel(
    application: Application,
    private val movieRepository: MovieRepository,
    private val dispatcher : CoroutineContext = Dispatchers.IO
) : AndroidViewModel(application){

    private val _argsMovie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel> = _argsMovie

    private val _detailMovieShow = MutableLiveData<Boolean>().apply { value = true }
    val detailMovieShow : LiveData<Boolean> = _detailMovieShow

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading : LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error : LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception>()
    val errorException : LiveData<Exception> = _errorException

    fun onNavArgs(data: DetailMovieActivityArgs?){
        _loading.value = true
        _error.value = false
        _detailMovieShow.value = false
        viewModelScope.launch(dispatcher) {
            if (data != null) {
                if (data.movie != null){
                    _detailMovieShow.postValue(true)
                    data.movie.apply {
                        homepage = if (homepage.isEmpty()) getString(R.string.unknown) else homepage
                    }
                    _argsMovie.postValue(data.movie)
                }else{
                    _error.postValue(true)
                    _errorException.postValue(Exception(getString(R.string.movie_id_not_found)))
                }
            }else{
                _error.postValue(true)
                _errorException.postValue(Exception(getString(R.string.movie_id_not_found)))
            }
            _loading.postValue(false)
        }
    }

    private fun getString(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

}