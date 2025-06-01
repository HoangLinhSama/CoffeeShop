package com.hoanglinhsama.client.presentation.viewmodel.event

import android.app.Activity

sealed class OrderStatusEvent {
    object TrackDeliveryEvent : OrderStatusEvent()
    data class CancelOrderEvent(val orderId: Int) : OrderStatusEvent()
    data class GetOrderStatusEvent(val orderId: Int) : OrderStatusEvent()
    data class ChangeMethodPaymentEvent(val methodPayment: String) : OrderStatusEvent()
    object ShowProcessStatusOrderEvent : OrderStatusEvent()
    data class PaymentEvent(
        val methodPayment: String,
        val orderId: Int,
        val activity: Activity,
        val callback: (Boolean, String) -> Unit,
    ) : OrderStatusEvent()

    data class UpdateStatePaymentEvent(
        val paymentBillId: String,
        val hasLaunchedPayment: Boolean,
        val orderId: Int,
        val statusId: Int,
        val callback: (String, Boolean?) -> Unit,
    ) : OrderStatusEvent()
}