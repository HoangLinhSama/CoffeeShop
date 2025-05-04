package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.common.UpdateDrinkOrderHolder
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
        receiveUpdateDrinkOrder()
//        calcFee()
    }

//    private fun calcFee() {
//        if (_state.value.listDrinkOrder?.isNotEmpty() == true && _state.value.listInformation != null) {
//            val address = state.value.listInformation?.get(2)?.lowercase()
//            val shippingFee = (if (!state.value.isDelivery) {
//                0
//            } else {
//                if (
//                    address?.contains("hồ chí minh") == true ||
//                    address?.contains("ho chi minh") == true ||
//                    address?.contains("hochiminh") == true ||
//                    address?.contains("hcm") == true
//                ) {
//                    15000
//                } else {
//                    30000
//                }
//            }).toFloat()
//            val disCount: Float = _state.value.disCount ?: 0F
//            val totalPayment = subTotal?.plus(shippingFee)?.minus(disCount)
//            _state.value = _state.value.copy(
//                _subTotal = subTotal,
//                _shippingFee = shippingFee,
//                _totalPayment = totalPayment
//            )
//        }
//    }

    private fun receiveUpdateDrinkOrder() {
        viewModelScope.launch {
            UpdateDrinkOrderHolder.updateDrinkOrder.collect { drink ->
                val listUpdateDrinkOrder = _state.value.listUpdateDrinkOrder?.toMutableList()
                if (_state.value.listUpdateDrinkOrder?.isNotEmpty() == true) {
                    val index = listUpdateDrinkOrder?.indexOfFirst {
                        it.id == drink?.id
                    }
                    if (index == -1) {
                        drink?.let {
                            listUpdateDrinkOrder.add(it)
                        }
                    }
                } else {
                    drink?.let {
                        listUpdateDrinkOrder?.add(it)
                    }
                }
                _state.value = _state.value.copy(_listUpdateDrinkOrder = listUpdateDrinkOrder)
            }
        }
    }

    private fun getTempOrder() {
        viewModelScope.launch {
            TempOrderHolder.tempOrder.collect { result ->
                if (result?.result is Result.Success) {
                    val data = (result.result as Result.Success).data
                    val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                    if (_state.value.listDrinkOrder?.isNotEmpty() == true) {
                        val index = listDrinkOrder?.indexOfFirst { drinkOrder ->
                            drinkOrder.id == data.id
                                    && drinkOrder.size == data.size
                                    && drinkOrder.listTopping == data.listTopping
                                    && drinkOrder.note == data.note
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
                    updateSubTotal()
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
                            updateDeliveryFee()
                        }
                    }
                }
            }
        }
    }

    private fun updateSubTotal() {
        val subTotal = _state.value.listDrinkOrder?.sumOf {
            it.price.toInt()
        }?.toFloat()
        _state.value = _state.value.copy(_subTotal = subTotal)
    }

    private fun updateDeliveryFee() {
        val address = state.value.listInformation?.get(2)?.lowercase()
        val shippingFee = (if (!state.value.isDelivery) {
            0
        } else {
            if (
                address?.contains("hồ chí minh") == true ||
                address?.contains("ho chi minh") == true ||
                address?.contains("hochiminh") == true ||
                address?.contains("hcm") == true
            ) {
                15000
            } else {
                30000
            }
        }).toFloat()
        _state.value = _state.value.copy(
            _shippingFee = shippingFee
        )
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OrderTypeClickEvent -> {
                _state.value = _state.value.copy(_isDelivery = event.isDelivery)
                updateDeliveryFee()
            }

            is OrderEvent.SelectBottomSheetShowEvent -> {
                _state.value =
                    _state.value.copy(_showBottomSheet = true, _bottomSheet = event.bottomSheet)
            }

            is OrderEvent.UpdateInfoDeliveryEvent -> {
                val listInformation = _state.value.listInformation?.toMutableList()
                listInformation?.set(event.index, event.value)
                _state.value = _state.value.copy(_listInformation = listInformation)
                if (event.index == 2) {
                    updateDeliveryFee()
                }
            }

            is OrderEvent.UpdateShowBottomSheetEvent -> {
                _state.value = _state.value.copy(_showBottomSheet = event.showBottomSheet)
            }

            is OrderEvent.FocusChangeEvent -> {
                val listFocus = _state.value.listInfoDeliveryFocus.toMutableList()
                listFocus[event.index] = event.isFocus
                _state.value = _state.value.copy(_listInfoDeliveryFocus = listFocus)
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

            is OrderEvent.UpdateCurrentlySwipedIndexEvent -> {
                _state.value = _state.value.copy(_currentlySwipedIndex = event.newIndex)
            }

            is OrderEvent.DeleteDrinkOrderEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                listDrinkOrder?.removeAt(event.index)
                _state.value =
                    _state.value.copy(_listDrinkOrder = listDrinkOrder, _currentlySwipedIndex = -1)
                updateSubTotal()
            }

            is OrderEvent.UpdateTempOrderEvent -> {
                _state.value =
                    _state.value.copy(
                        _bottomSheet = BottomSheetContent.BottomSheetUpdateTempOrder,
                        _showBottomSheet = true,
                        _indexUpdateOrderDrink = event.indexDinkOrder,
                        _currentlySwipedIndex = -1
                    )
            }

            is OrderEvent.UpdateNoteFocusChangeEvent -> {
                _state.value = _state.value.copy(_updateNoteFocus = event.isFocus)
            }

            is OrderEvent.UpdateNoteEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                updateDrinkOrder = updateDrinkOrder?.copy(_note = event.note)
                updateDrinkOrder.let {
                    it?.let { element ->
                        listDrinkOrder?.set(
                            state.value.indexUpdateOrderDrink,
                            element
                        )
                    }
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
            }

            is OrderEvent.UpdateSizeEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                val priceSizeOld =
                    event.drink.priceSize?.get(updateDrinkOrder?.size)!!
                val priceSizeNew = event.drink.priceSize[event.size]!!
                val newPrice =
                    (priceSizeNew - priceSizeOld) * updateDrinkOrder!!.count + updateDrinkOrder.price
                updateDrinkOrder =
                    updateDrinkOrder.copy(_size = event.size, _price = newPrice)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
            }

            is OrderEvent.UpdateToppingEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                val listUpdateTopping = updateDrinkOrder?.listTopping?.toMutableList()
                val listTopping = event.drink.toppingPrice?.keys?.toList()
                val topping = listTopping?.get(event.index)
                val totalToppingPriceOld = updateDrinkOrder?.listTopping?.sumOf {
                    event.drink.toppingPrice?.get(it) ?: 0
                } ?: 0
                if (event.isSelect) {
                    listUpdateTopping?.add(topping.toString())
                } else {
                    listUpdateTopping?.remove(topping.toString())
                }
                updateDrinkOrder =
                    updateDrinkOrder?.copy(_listTopping = listUpdateTopping)
                val totalToppingPriceNew = updateDrinkOrder?.listTopping?.sumOf {
                    event.drink.toppingPrice?.get(it) ?: 0
                } ?: 0
                val price =
                    (totalToppingPriceNew.minus(totalToppingPriceOld)).times(
                        updateDrinkOrder?.count
                            ?: 1
                    )
                        .plus(updateDrinkOrder!!.price)
                updateDrinkOrder = updateDrinkOrder.copy(_price = price)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
            }

            is OrderEvent.UpdateCountDrinkEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                val price =
                    (updateDrinkOrder!!.price / updateDrinkOrder.count) * event.quantity
                updateDrinkOrder = updateDrinkOrder.copy(_price = price, _count = event.quantity)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
            }
        }
    }
}