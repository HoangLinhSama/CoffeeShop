package com.hoanglinhsama.client.domain.model

data class Drink(
    private val _name: String,
    private val _priceSize: Map<String, Int>?,
    private val _picture: String,
    private val _star: Float?,
    private val _description: String,
    private val _toppingPrice: Map<String, Int>?,
) {
    val name = _name
    val priceSize = _priceSize
    val picture = _picture
    val star = _star
    val description = _description
    val toppingPrice = _toppingPrice
}
