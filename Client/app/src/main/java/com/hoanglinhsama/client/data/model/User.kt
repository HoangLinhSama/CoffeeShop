package com.hoanglinhsama.client.data.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val address: String,
    val image: String?,
    val memberShip: Int,
    val currentBean :Int,
    val collectedBean :Int
)