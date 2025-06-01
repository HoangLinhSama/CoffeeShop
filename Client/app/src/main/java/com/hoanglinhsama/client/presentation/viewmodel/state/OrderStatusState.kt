package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.OrderStatus

data class OrderStatusState(
    private val _orderStatus: OrderStatus? = null,
    private val _paymentBillId: String? = null,
    private val _hasLaunchedPayment: Boolean = false,
) {
    val orderStatus = _orderStatus
    val paymentBillId = _paymentBillId
    val hasLaunchedPayment = _hasLaunchedPayment
}
