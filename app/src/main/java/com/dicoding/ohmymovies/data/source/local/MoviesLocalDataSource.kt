package com.dicoding.ohmymovies.data.source.local

import android.content.Context
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.source.MovieDataSource
import com.dicoding.ohmymovies.util.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesLocalDataSource : MovieDataSource{

    private val gson = Gson()
    private val moviesFilename = "movies.json"
    private val tvshowFilename = "tvshows.json"

    private fun Context.generateImageResource(name : String) : Int {
        return resources.getIdentifier(name, "drawable", packageName)
    }

    override suspend fun getMovies(context : Context): Result<List<MovieModel>> {
        return try{
            val text = Util.readFromFile(context, moviesFilename)
            return if (text != null) {
                val result = mutableListOf<MovieModel>()
                val type = object : TypeToken<List<MovieModel>>(){}.type
                val list = gson.fromJson<List<MovieModel>>(text, type)
                withContext(Dispatchers.Default){
                    list.forEach{
                        val cast = it
                        cast.posterImageResource = context.generateImageResource(it.posterPath ?: "")
                        result.add(cast)
                    }
                }
                Result.Success(result)
            }else{
                Result.Error(Exception("Text empty"))
            }
        }catch (e : Exception){
            Result.Error(e)
        }
    }

    override suspend fun getTvshows(context: Context): Result<List<TvShowModel>> {
        val text = Util.readFromFile(context, tvshowFilename)
        return if (text != null) {
            val result = mutableListOf<TvShowModel>()
            val type = object : TypeToken<List<TvShowModel>>(){}.type
            val list = gson.fromJson<List<TvShowModel>>(text, type)
            withContext(Dispatchers.Default){
                list.forEach{
                    val cast = it
                    cast.posterImageResource = context.generateImageResource(it.posterPath ?: "")
                    result.add(cast)
                }
            }
            Result.Success(result)
        }else{
            Result.Error(Exception(""))
        }
    }
}