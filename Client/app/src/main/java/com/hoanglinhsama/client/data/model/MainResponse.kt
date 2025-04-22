package com.hoanglinhsama.client.data.model

data class MainResponse<T>(val status: String, val result: List<T>? = null)