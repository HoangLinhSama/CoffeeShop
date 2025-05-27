package com.hoanglinhsama.client.domain.model

data class OrderStatus(
    private val _isDelivery: Boolean,
    private val _listStatus: List<Status>,
    private val _name: String?,
    private val _phone: String?,
    private val _address: String?,
    private val _listDrinkOrder: List<DrinkOrder>,
    private val _shopName: String?,
    private val _shopAddress: String?,
    private val _subTotal: Float,
    private val _deliveryFee: Float,
    private val _discount: Float?,
    private val _totalPrice: Float,
    private val _methodPayment: String,
    private val _quantityBeanUse: Int?,
    private val _voucherName: String?,
) {
    val isDelivery = _isDelivery
    val listStatus = _listStatus
    val name = _name
    val phone = _phone
    val address = _address
    val listDrinkOrder = _listDrinkOrder
    val shopName = _shopName
    val shopAddress = _shopAddress
    val subTotal = _subTotal
    val deliveryFee = _deliveryFee
    val discount = _discount
    val totalPrice = _totalPrice
    val methodPayment = _methodPayment
    val quantityBeanUse = _quantityBeanUse
    val voucherName = _voucherName
}
