package com.hoanglinhsama.client.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.GetOrderStatusUseCase
import com.hoanglinhsama.client.domain.usecase.main.PayWithZaloPayUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(
    private val getOrderStatusUseCase: GetOrderStatusUseCase,
    private val payWithZaloPayUseCase: PayWithZaloPayUseCase,
) :
    ViewModel() {
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
                _state.value = _state.value.copy(_hasLaunchedPayment = true)
                when (event.methodPayment) {
                    "ZaloPay" -> {
//                        val orderZaloPay = OrderZaloPay()
//                        payWithZaloPayUseCase()
                    }
                }
            }
        }
    }
}