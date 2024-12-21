package com.hoanglinhsama.client.domain.model

data class User(
    private val _name: String,
    private val _image: String?,
) {
    val name
        get() = _name
    val image
        get() = _image
}
