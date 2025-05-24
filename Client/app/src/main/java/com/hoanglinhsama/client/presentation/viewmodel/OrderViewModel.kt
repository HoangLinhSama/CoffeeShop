package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.domain.usecase.main.InsertOrderUseCase
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.common.UpdateDrinkOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.UiEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.UiEvent.ShowToast
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(OrderState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getInfoDelivery()
        getTempOrder()
        receiveUpdateDrinkOrder()
        initListMethodPayment()
    }

    private fun initListMethodPayment() {
        val listMethodPayment = listOf(
            FeatureItem(R.drawable.ic_cash, "Tiền mặt", null, null),
            FeatureItem(R.drawable.ic_zalopay, "ZaloPay", null, null),
            FeatureItem(R.drawable.ic_momo, "MoMo", null, null),
            FeatureItem(R.drawable.ic_vnpay, "VNPay", null, null),
            FeatureItem(R.drawable.ic_shopeepay, "ShopeePay", null, null),
            FeatureItem(R.drawable.ic_bank_card, "Thẻ ngân hàng", null, null),
            FeatureItem(R.drawable.ic_paypal, "PayPal", null, null),
        )
        _state.value = _state.value.copy(_listMethodPayment = listMethodPayment)
    }

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
                            drinkOrder.id == data.id && drinkOrder.size == data.size && drinkOrder.listTopping == data.listTopping && drinkOrder.note == data.note
                        }
                        index?.let { index ->
                            if (index != -1) {
                                val drinkOrder = listDrinkOrder[index]
                                listDrinkOrder[index] = drinkOrder.copy(
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
                    updateTotalPayment()
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
                                ), _currentBean = it.data.currentBean,
                                _userId = it.data.id
                            )
                            updateDeliveryFee()
                        }
                    }
                }
            }
        }
    }

    private fun updateTotalPayment() {
        val totalPayment = _state.value.shippingFee?.let {
            _state.value.subTotal?.plus(it)?.minus(_state.value.disCount)
        }
        _state.value = _state.value.copy(_totalPayment = totalPayment)
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
            if (address?.contains("hồ chí minh") == true || address?.contains("ho chi minh") == true || address?.contains(
                    "hochiminh"
                ) == true || address?.contains("hcm") == true
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

    private fun checkVoucherAvailability() {
        _state.value.voucher?.let {
            val typeOrder = if (_state.value.isDelivery) "delivery" else "takeaway"
            val quantity = _state.value.listDrinkOrder?.sumOf {
                it.count
            }
            var quantityCondition by Delegates.notNull<Int>()
            var priceCondition by Delegates.notNull<Int>()
            val listDrinkCategory =
                _state.value.listDrinkOrder?.map { it.drinkCategory }?.distinct()
            val drinkCategoryCondition = listDrinkCategory?.any { drinkCategory ->
                drinkCategory in it.categoryDrink
            }
            if (it.conditions.toString().length < 4) {
                quantityCondition = it.conditions.toString().toInt()
                if (it.type != typeOrder || quantity!! < quantityCondition || drinkCategoryCondition == false) {
                    _state.value = _state.value.copy(_voucher = null, _disCount = 0F)
                    updateDeliveryFee()
                    state.value.listDrinkOrder?.let { listDrinkOrder ->
                        if (listDrinkOrder.isNotEmpty()) {
                            viewModelScope.launch {
                                _eventFlow.emit(ShowToast("Voucher không còn khả dụng"))
                            }
                        }
                    }
                }
            } else {
                priceCondition = it.conditions.toString().toInt()
                if (it.type != typeOrder || state.value.subTotal!! < priceCondition || drinkCategoryCondition == false) {
                    _state.value = _state.value.copy(_voucher = null, _disCount = 0F)
                    updateDeliveryFee()
                    state.value.listDrinkOrder?.let { listDrinkOrder ->
                        if (listDrinkOrder.isNotEmpty()) {
                            viewModelScope.launch {
                                _eventFlow.emit(ShowToast("Voucher không còn khả dụng"))
                            }
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
                checkVoucherAvailability()
                updateDeliveryFee()
                updateTotalPayment()
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
                    updateTotalPayment()
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
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateTempOrderEvent -> {
                _state.value = _state.value.copy(
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
                            state.value.indexUpdateOrderDrink, element
                        )
                    }
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
            }

            is OrderEvent.UpdateSizeEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                val priceSizeOld = event.drink.priceSize?.get(updateDrinkOrder?.size)!!
                val priceSizeNew = event.drink.priceSize[event.size]!!
                val newPrice =
                    (priceSizeNew - priceSizeOld) * updateDrinkOrder!!.count + updateDrinkOrder.price
                updateDrinkOrder = updateDrinkOrder.copy(_size = event.size, _price = newPrice)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
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
                updateDrinkOrder = updateDrinkOrder?.copy(_listTopping = listUpdateTopping)
                val totalToppingPriceNew = updateDrinkOrder?.listTopping?.sumOf {
                    event.drink.toppingPrice?.get(it) ?: 0
                } ?: 0
                val price = (totalToppingPriceNew.minus(totalToppingPriceOld)).times(
                    updateDrinkOrder?.count ?: 1
                ).plus(updateDrinkOrder!!.price)
                updateDrinkOrder = updateDrinkOrder.copy(_price = price)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateCountDrinkEvent -> {
                val listDrinkOrder = _state.value.listDrinkOrder?.toMutableList()
                var updateDrinkOrder = listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
                val price = (updateDrinkOrder!!.price / updateDrinkOrder.count) * event.quantity
                updateDrinkOrder = updateDrinkOrder.copy(_price = price, _count = event.quantity)
                updateDrinkOrder.let {
                    listDrinkOrder?.set(state.value.indexUpdateOrderDrink, it)
                }
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateVoucherEvent -> {
                _state.value = _state.value.copy(_voucher = event.voucher)
                _state.value.voucher?.value?.let {
                    viewModelScope.launch {
                        _eventFlow.emit(ShowToast("Đã áp dụng Voucher"))
                    }
                    if (it < 1) {
                        _state.value.subTotal?.times(it)?.let { discount ->
                            _state.value =
                                _state.value.copy(_disCount = discount)
                            updateTotalPayment()
                        }
                    } else {
                        _state.value = _state.value.copy(_disCount = it)
                        updateTotalPayment()
                    }
                    if (_state.value.voucher?.freeShip == true) {
                        _state.value = _state.value.copy(_shippingFee = 0F)
                        updateTotalPayment()
                    } else {
                        updateDeliveryFee()
                        updateTotalPayment()
                    }
                }
            }

            is OrderEvent.ResetEvent -> {
                _state.value = OrderState()
                getInfoDelivery()
                receiveUpdateDrinkOrder()
                initListMethodPayment()
            }

            is OrderEvent.UpdatePaymentSelectedEvent -> {
                _state.value = _state.value.copy(_indexPaymentSelected = event.index)
            }

            is OrderEvent.UpdateUseBeanEvent -> {
                _state.value = _state.value.copy(
                    _useBean = event.useBean
                )
                if (event.useBean) {
                    _state.value.currentBean?.times(1000)?.toFloat()?.let {
                        _state.value = _state.value.copy(_voucher = null, _disCount = it)
                        viewModelScope.launch {
                            _eventFlow.emit(ShowToast("Đã áp dụng đổi bean"))
                        }
                    }
                } else {
                    _state.value = _state.value.copy(_disCount = 0F, _showDialog = false)
                    viewModelScope.launch {
                        _eventFlow.emit(ShowToast("Đã hủy áp dụng đổi bean"))
                    }
                }
                updateTotalPayment()
            }

            OrderEvent.UseVoucherLaterEvent -> {
                _state.value = _state.value.copy(_voucher = null, _disCount = 0F)
                updateDeliveryFee()
                updateTotalPayment()
                viewModelScope.launch {
                    _eventFlow.emit(ShowToast("Đã huỷ áp dụng Voucher"))
                }
            }

            is OrderEvent.UpdateShowDialog -> {
                _state.value = _state.value.copy(_showDialog = event.showDialog)
            }

            is OrderEvent.SelectDialogShowEvent -> {
                _state.value = _state.value.copy(_dialog = event.dialog, _showDialog = true)
            }

            is OrderEvent.SendOrderEvent -> {
                _state.value = _state.value.copy(_showDialog = false)
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        _orderResultFlow =
                            insertOrderUseCase(
                                event.userId,
                                event.name,
                                event.phone,
                                event.address,
                                event.dateTime,
                                event.quantityBeanUse,
                                event.paymentMethod,
                                event.shopId,
                                event.isDelivery,
                                event.deliveryFee,
                                event.subTotal,
                                event.totalPrice,
                                event.voucherId,
                                event.paymentBillId,
                                event.listDrinkOrder
                            )
                    )
                }
            }

            is OrderEvent.UpdateResultOrder -> {
                _state.value = _state.value.copy(_resultOrder = event.result)
            }

            is OrderEvent.UpdateOrderId -> {
                _state.value = _state.value.copy(_orderId = event.orderId)
                event.callback(event.orderId)
            }
        }
    }
}