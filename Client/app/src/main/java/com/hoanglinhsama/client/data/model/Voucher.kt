package com.hoanglinhsama.client.data.model

data class Voucher(
    val code: String,
    val startDate: String,
    val expirationDate: String,
    val name: String,
    val description: String,
    val value: Float,
    val type: String,
    val freeShip: Boolean,
    val conditions: Int,
    val categoryDrink: List<String>,
    val picture: String,
    val qrCode:String
)