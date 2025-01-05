package com.hoanglinhsama.client.data.model

data class User(
    val id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val coffeeBean: Int,
    val membership: String,
    val storeId: Int?,
    val type: String,
    val image: String?,
)