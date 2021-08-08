package com.example.capiter.data.model

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import retrofit2.HttpException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(@Expose val data: T) : Result<T>()
    data class Error(@Expose val exception: HttpException) : Result<Void>() {
        constructor(throwable: Throwable) : this(throwable as HttpException)

        fun getApiError(): ApiError? {
            return Gson().fromJson(
                exception.response()?.errorBody()?.string(),
                ApiError::class.java
            )
        }
    }

    object Failure : Result<Void>()

    object Void : Result<Void>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${getApiError()}]"
            else -> "Failure"
        }
    }
}