package com.hoanglinhsama.client.data.model

data class OrderZaloPay(
    val appId: String,
    val appUser: String,
    val appTransId: String,
    val appTime: String,
    val amount: Float,
    val item: String,
    val description: String,
    val embedData: String,
    val bankCode: String,
    val mac: String,
)