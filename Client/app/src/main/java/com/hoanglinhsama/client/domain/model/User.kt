package com.hoanglinhsama.client.domain.model

data class User(
    private val _id: Int,
    private val _firstName: String,
    private val _lastName: String,
    private val _phone: String,
    private val _address: String,
    private val _image: String?,
) {
    val id = _id
    val firstName = _firstName
    val lastName = _lastName
    val phone = _phone
    val address = _address
    val image = _image
}
