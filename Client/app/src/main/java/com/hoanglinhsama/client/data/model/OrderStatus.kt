package com.hoanglinhsama.client.data.model

data class OrderStatus(
    val isDelivery: Boolean,
    val listStatus: List<Status>,
    val name: String?,
    val phone: String?,
    val address: String?,
    val listDrinkOrder: List<DrinkOrder>,
    val shopName: String?,
    val shopAddress: String?,
    val subTotal: Float,
    val deliveryFee: Float,
    val discount: Float?,
    val totalPrice: Float,
    val methodPayment: String,
    val quantityBeanUse: Int?,
    val voucherName: String?,
)
