package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.GetOrderHistoryUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(private val getOrderHistoryUseCase: GetOrderHistoryUseCase) :
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
                    getOrderHistoryUseCase.getOrderStatus(event.orderId).collect {
                        when (it) {
                            is Result.Error -> {

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
        }
    }
}