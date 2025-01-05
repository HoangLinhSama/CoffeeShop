package com.hoanglinhsama.client.domain.model

data class Voucher(
    private val _startDate: String,
    private val _expirationDate: String,
    private val _name: String,
    private val _conditions: Int,
    private val _categoryDrink: List<String>,
    private val _picture: String,
    private val _freeShip: Boolean,
) {
    val startDate = _startDate
    val expirationDate = _expirationDate
    val name = _name
    val conditions = _conditions
    val categoryDrink = _categoryDrink
    val picture = _picture
    val freeShip = _freeShip
}
