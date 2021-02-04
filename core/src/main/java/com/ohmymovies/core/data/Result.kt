package com.ohmymovies.core.data

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    data class Loading(val state: Boolean) : Result<Nothing>()
}