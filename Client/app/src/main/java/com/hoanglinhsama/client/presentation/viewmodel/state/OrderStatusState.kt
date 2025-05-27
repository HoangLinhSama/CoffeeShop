package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.OrderStatus

data class OrderStatusState(
    private val _orderStatus: OrderStatus? = null,
    private val _paymentBillId: String? = null,
) {
    val orderStatus = _orderStatus
    val paymentBillId = _paymentBillId
}
