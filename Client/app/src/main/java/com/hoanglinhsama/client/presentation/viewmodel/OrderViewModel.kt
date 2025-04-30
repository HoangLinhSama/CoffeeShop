package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(OrderState())
    val state = _state

    init {
        getInfoDelivery()
        getTempOrder()
    }

    private fun getTempOrder() {
        viewModelScope.launch {
            TempOrderHolder.tempOrder.collect { result ->
                if (result?.result is Result.Success) {
                    val data = (result.result as Result.Success).data
                    val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                    if (_state.value.listDrinkOrder?.isNotEmpty() == true) {
                        val index = listDrinkOrder?.indexOfFirst { drinkOrder ->
                            drinkOrder.name == data.name
                                    && drinkOrder.size == data.size
                                    && drinkOrder.listTopping == data.listTopping
                                    && drinkOrder.note == data.note
                                    && drinkOrder.picture == data.picture
                        }
                        index?.let { index ->
                            if (index != -1) {
                                val drinkOrder = listDrinkOrder[index]
                                listDrinkOrder[index] =
                                    drinkOrder.copy(
                                        _count = drinkOrder.count + data.count,
                                        _price = drinkOrder.price + data.price
                                    )
                            } else {
                                listDrinkOrder.add(data)
                            }
                        }
                    } else {
                        listDrinkOrder?.add(data)
                    }
                    _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                }
            }
        }
    }

    private fun getInfoDelivery() {
        viewModelScope.launch {
            getPhoneUseCase().collect {
                if (it != "") {
                    getUserUseCase(it).collect {
                        if (it is Result.Success) {
                            _state.value = _state.value.copy(
                                _listInformation = listOf(
                                    it.data.firstName + " " + it.data.lastName,
                                    it.data.phone,
                                    it.data.address
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OrderTypeClickEvent -> {
                _state.value = _state.value.copy(_isDelivery = event.isDelivery)
            }

            is OrderEvent.SelectBottomSheetShowEvent -> {
                _state.value =
                    _state.value.copy(_showBottomSheet = true, _bottomSheet = event.bottomSheet)
            }

            is OrderEvent.UpdateInfoDeliveryEvent -> {
                val listInformation = _state.value.listInformation?.toMutableList()
                listInformation?.set(event.index, event.value)
                _state.value = _state.value.copy(_listInformation = listInformation)
            }

            is OrderEvent.UpdateShowBottomSheetEvent -> {
                _state.value = _state.value.copy(_showBottomSheet = event.showBottomSheet)
            }

            is OrderEvent.FocusChangeEvent -> {
                val listFocus = _state.value.listTextFieldFocus.toMutableList()
                listFocus[event.index] = event.isFocus
                _state.value = _state.value.copy(_listTextFieldFocus = listFocus)
            }

            is OrderEvent.UpdateSelectModeEvent -> {
                _state.value = _state.value.copy(_shopScreenIsSelectMode = event.isSelectMode)
            }

            is OrderEvent.UpdateSelectShopEvent -> {
                _state.value = _state.value.copy(
                    _shopName = event.shop.name,
                    _shopScreenIsSelectMode = event.isSelectMode,
                    _shopId = event.shop.id
                )
            }
        }
    }
}