package com.hoanglinhsama.client.domain.model

data class DrinkOrder(
    private val _id: Int,
    private val _picture: String,
    private val _name: String,
    private val _size: String?,
    private val _listTopping: List<String>?,
    private val _note: String?,
    private val _count: Int,
    private val _price: Float,
    private val _drinkCategory: String,
) {
    val id = _id
    val picture = _picture
    val name = _name
    val size = _size
    val listTopping = _listTopping
    val note = _note
    val count = _count
    val price = _price
    val drinkCategory = _drinkCategory
}