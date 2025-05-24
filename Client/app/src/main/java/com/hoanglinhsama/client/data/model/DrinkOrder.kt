package com.hoanglinhsama.client.data.model

class DrinkOrder(
    val id: Int,
    val picture: String,
    val name: String,
    val size: String?,
    val listTopping: List<String>?,
    val note: String?,
    val count: Int,
    val price: Float,
    val drinkCategory: String,
)