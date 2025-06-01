package com.hoanglinhsama.client.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.CreateOrderZaloPayUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetOrderStatusUseCase
import com.hoanglinhsama.client.domain.usecase.main.InsertStatusOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdatePaymentBillIdUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(
    private val getOrderStatusUseCase: GetOrderStatusUseCase,
    private val createOrderZaloPayUseCase: CreateOrderZaloPayUseCase,
    private val insertStatusOrderUseCase: InsertStatusOrderUseCase,
    private val updatePaymentBillIdUseCase: UpdatePaymentBillIdUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(OrderStatusState())
    val state = _state
    fun onEvent(event: OrderStatusEvent) {
        when (event) {
            is OrderStatusEvent.TrackDeliveryEvent -> {

            }

            is OrderStatusEvent.CancelOrderEvent -> {

            }

            is OrderStatusEvent.GetOrderStatusEvent -> {
                viewModelScope.launch {
                    getOrderStatusUseCase(event.orderId).collect {
                        when (it) {
                            is Result.Error -> {
                                Log.d("TAG", "GetOrderStatusUseCase: ${it.exception}")
                            }

                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                _state.value = _state.value.copy(_orderStatus = it.data)
                            }
                        }
                    }
                }
            }

            is OrderStatusEvent.ChangeMethodPaymentEvent -> {

            }

            OrderStatusEvent.ShowProcessStatusOrderEvent -> {

            }

            is OrderStatusEvent.PaymentEvent -> {
                when (event.methodPayment) {
                    "ZaloPay" -> {
                        viewModelScope.launch {
                            val items = listOf<OrderZaloPayItems>(
                                OrderZaloPayItems(
                                    event.orderId,
                                    state.value.orderStatus!!.totalPrice,
                                    state.value.orderStatus!!.listDrinkOrder.sumOf { it.count })
                            )
                            val data = createOrderZaloPayUseCase(
                                "user" + state.value.orderStatus!!.userId,
                                event.orderId,
                                state.value.orderStatus!!.totalPrice.toLong(),
                                Gson().toJson(items)
                            )
                            try {
                                val code = data?.getString("return_code")
                                if (code == "1") {
                                    val tokenOrderZaloPay = data.getString("zp_trans_token")
                                    ZaloPaySDK.getInstance().payOrder(
                                        event.activity,
                                        tokenOrderZaloPay,
                                        "demozpdk://app",
                                        object : PayOrderListener {
                                            override fun onPaymentSucceeded(
                                                transactionId: String,
                                                zpTranstoken: String,
                                                appTransId: String,
                                            ) {
                                                event.callback(true, transactionId)
                                            }

                                            override fun onPaymentCanceled(
                                                zpTranstoken: String,
                                                appTransId: String,
                                            ) {
                                                event.callback(
                                                    false, "Người dùng đã hủy thanh toán"
                                                )
                                            }

                                            override fun onPaymentError(
                                                errorCode: ZaloPayError,
                                                zpTranstoken: String,
                                                appTransId: String,
                                            ) {
                                                event.callback(
                                                    false, "Thanh toán thất bại: $errorCode"
                                                )
                                            }
                                        })
                                } else {
                                    event.callback(
                                        false,
                                        "${data?.getString("return_message")}: ${data?.getString("sub_return_message")}"
                                    )
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.d("HLSM", "Exception: $e.message")
                            }
                        }
                    }
                }
            }

            is OrderStatusEvent.UpdateStatePaymentEvent -> {
                _state.value = _state.value.copy(_hasLaunchedPayment = event.hasLaunchedPayment)
                viewModelScope.launch {
                    insertStatusOrderUseCase(event.orderId, event.statusId).collect {
                        if (it is Result.Success) {
                            updatePaymentBillIdUseCase(
                                event.orderId,
                                event.paymentBillId,
                                event.callback
                            ).collect {

                            }
                        }
                    }
                }
            }
        }
    }
}

data class OrderZaloPayItems(val itemId: Int, val itemPrice: Float, val itemQuantity: Int)