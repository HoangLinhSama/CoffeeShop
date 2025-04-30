package com.hoanglinhsama.client.domain.model

import java.io.Serializable

data class Shop(
    private val _id: Int,
    private val _name: String,
    private val _picture: String,
    private val _address: String,
    private val _phone: String,
    private val _operatingHour: String,
) : Serializable {
    val id = _id
    val name = _name
    val picture = _picture
    val address = _address
    val phone = _phone
    val operatingHour = _operatingHour
}
