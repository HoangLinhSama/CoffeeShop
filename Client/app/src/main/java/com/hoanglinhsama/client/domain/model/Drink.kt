package com.hoanglinhsama.client.domain.model

data class Drink(
    private val _name: String,
    private val _priceSize: Map<String, Int>?,
    private val _picture: String,
    private val _star: Float?,
    private val _description: String,
    private val _toppingPrice: Map<String, Int>?,
) {
    val name
        get() = _name
    val priceSize
        get() = _priceSize
    val picture
        get() = _picture
    val star
        get() = _star
    val description
        get() = _description
    val toppingPrice
        get() = _toppingPrice
}
