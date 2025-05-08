package com.hoanglinhsama.client.domain.model

data class Voucher(
    private val _code: String,
    private val _startDate: String,
    private val _expirationDate: String,
    private val _name: String,
    private val _description: String,
    private val _value: Float,
    private val _type: String,
    private val _freeShip: Boolean,
    private val _conditions: Int,
    private val _categoryDrink: List<String>,
    private val _picture: String,
    private val _qrCode: String,
) {
    val code = _code
    val startDate = _startDate
    val expirationDate = _expirationDate
    val name = _name
    val description = _description
    val value = _value
    val type = _type
    val conditions = _conditions
    val categoryDrink = _categoryDrink
    val picture = _picture
    val freeShip = _freeShip
    val qrCode = _qrCode
}
