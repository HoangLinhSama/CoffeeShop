package com.hoanglinhsama.client.data.model

data class Drink(
    val id:Int,
    val name: String,
    val picture: String,
    val star: Float?,
    val description: String,
    val priceSize: List<String>,
    val toppingPrice: List<String>?,
    val countReview:Int
)
