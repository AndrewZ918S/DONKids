package ru.donkids.mobile.util

sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Error<T>(val message: String, val critical: Boolean = true): Resource<T>()
    class Loading<T>(val isLoading: Boolean = true): Resource<T>()
}
