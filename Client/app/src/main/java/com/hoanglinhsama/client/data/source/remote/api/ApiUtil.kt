package com.hoanglinhsama.client.data.source.remote.api

object ApiUtil {
    private const val localhost = "192.168.1.7"
    const val _BASE_URL = "http://$localhost/CoffeeShop/Server/"

    var BASE_URL: String = ""
        get() = _BASE_URL
}