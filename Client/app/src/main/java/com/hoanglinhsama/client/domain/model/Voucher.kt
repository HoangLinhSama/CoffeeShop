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
    val startDate
        get() = _startDate
    val expirationDate
        get() = _expirationDate
    val name
        get() = _name
    val conditions
        get() = _conditions
    val categoryDrink
        get() = _categoryDrink
    val picture
        get() = _picture
    val freeShip
        get() = _freeShip
}
