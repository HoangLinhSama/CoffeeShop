package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(OrderStatusState())
    val state = _state
    fun onEvent(event: OrderStatusEvent) {

    }
}