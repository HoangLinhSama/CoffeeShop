package com.hoanglinhsama.client.domain.model

data class User(
    private val _id: Int,
    private val _name: String,
    private val _image: String?,
) {
    val id = _id
    val name = _name
    val image = _image
}
