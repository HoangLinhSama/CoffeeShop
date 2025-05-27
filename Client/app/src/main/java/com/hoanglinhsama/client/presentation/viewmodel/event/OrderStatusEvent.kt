package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class OrderStatusEvent {
    object TrackDeliveryEvent : OrderStatusEvent()
    data class CancelOrderEvent(val orderId: Int) : OrderStatusEvent()
    data class GetOrderStatusEvent(val orderId: Int) : OrderStatusEvent()
    data class ChangeMethodPaymentEvent(val methodPayment: String) : OrderStatusEvent()
    object ShowProcessStatusOrderEvent : OrderStatusEvent()
}