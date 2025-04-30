package com.hoanglinhsama.client.data.model

import java.util.UUID

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

data class UniqueResult<T>(
    val id: String = UUID.randomUUID().toString(),
    val result: Result<T>,
)
