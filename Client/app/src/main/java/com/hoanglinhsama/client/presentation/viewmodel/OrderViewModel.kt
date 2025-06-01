package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.usecase.main.CalculateDeliveryFeeUseCase
import com.hoanglinhsama.client.domain.usecase.main.CalculateSubtotalUseCase
import com.hoanglinhsama.client.domain.usecase.main.CalculateTotalPaymentUseCase
import com.hoanglinhsama.client.domain.usecase.main.CheckVoucherAvailabilityUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.domain.usecase.main.InsertOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.ReceiveTempOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.ReceiveUpdateDrinkOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateCountDrinkOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateSizeDrinkOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateToppingDrinkOrderUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateUseBeanUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateUseVoucherUseCase
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

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
    private val receiveTempOrderUseCase: ReceiveTempOrderUseCase,
    private val calculateDeliveryFeeUseCase: CalculateDeliveryFeeUseCase,
    private val calculateSubTotalUseCase: CalculateSubtotalUseCase,
    private val calculateTotalPaymentUseCase: CalculateTotalPaymentUseCase,
    private val receiveUpdateDrinkOrderUseCase: ReceiveUpdateDrinkOrderUseCase,
    private val checkVoucherAvailabilityUseCase: CheckVoucherAvailabilityUseCase,
    private val updateSizeDrinkOrderUseCase: UpdateSizeDrinkOrderUseCase,
    private val updateToppingDrinkOrderUseCase: UpdateToppingDrinkOrderUseCase,
    private val updateCountDrinkOrderUseCase: UpdateCountDrinkOrderUseCase,
    private val updateUseVoucherUseCase: UpdateUseVoucherUseCase,
    private val updateUseBeanUseCase: UpdateUseBeanUseCase,
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
                val listUpdateDrinkOrder =
                    receiveUpdateDrinkOrderUseCase(_state.value.listUpdateDrinkOrder, drink)
                _state.value = _state.value.copy(_listUpdateDrinkOrder = listUpdateDrinkOrder)
            }
        }
    }

    private fun getTempOrder() {
        viewModelScope.launch {
            TempOrderHolder.tempOrder.collect { result ->
                if (result?.result is Result.Success) {
                    val data = (result.result as Result.Success).data
                    val listDrinkOrder = receiveTempOrderUseCase(_state.value.listDrinkOrder, data)
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
        val totalPayment = calculateTotalPaymentUseCase(
            _state.value.shippingFee,
            _state.value.subTotal,
            _state.value.disCount
        )
        _state.value = _state.value.copy(_totalPayment = totalPayment)
    }

    private fun updateSubTotal() {
        val subTotal = calculateSubTotalUseCase(_state.value.listDrinkOrder)
        _state.value = _state.value.copy(_subTotal = subTotal)
    }

    private fun updateDeliveryFee() {
        val shippingFee = calculateDeliveryFeeUseCase(
            state.value.listInformation?.get(2)?.lowercase(),
            state.value.isDelivery
        )
        _state.value = _state.value.copy(
            _shippingFee = shippingFee
        )
    }

    private fun checkVoucherAvailability() {
        _state.value.voucher?.let {
            val isAvailable = checkVoucherAvailabilityUseCase(
                it,
                _state.value.listDrinkOrder,
                _state.value.isDelivery,
                _state.value.subTotal!!
            )
            if (!isAvailable) {
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

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.OrderTypeClickEvent -> {
                _state.value = _state.value.copy(_isDelivery = event.isDelivery)
                val listPaymentMethod = _state.value.listMethodPayment?.toMutableList()
                if (_state.value.isDelivery) {
                    val hasCash = listPaymentMethod?.any { it.title == "Tiền mặt" } == true
                    if (!hasCash) {
                        listPaymentMethod?.add(
                            0,
                            FeatureItem(R.drawable.ic_cash, "Tiền mặt", null, null)
                        )
                    }
                } else {
                    if (listPaymentMethod?.firstOrNull()?.title == "Tiền mặt") {
                        listPaymentMethod.removeAt(0)
                    }
                }
                _state.value = _state.value.copy(_listMethodPayment = listPaymentMethod)
                checkVoucherAvailability()
                updateDeliveryFee()
                updateTotalPayment()
            }

            is OrderEvent.SelectBottomSheetShowEvent -> {
                _state.value =
                    _state.value.copy(
                        _showBottomSheet = true,
                        _bottomSheet = event.bottomSheet
                    )
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
                _state.value =
                    _state.value.copy(_shopScreenIsSelectMode = event.isSelectMode)
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
                    _state.value.copy(
                        _listDrinkOrder = listDrinkOrder,
                        _currentlySwipedIndex = -1
                    )
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
                var updateDrinkOrder =
                    listDrinkOrder?.get(state.value.indexUpdateOrderDrink)
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
                val listDrinkOrder = updateSizeDrinkOrderUseCase(
                    _state.value.listDrinkOrder,
                    _state.value.indexUpdateOrderDrink,
                    event.size,
                    event.drink
                )
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateToppingEvent -> {
                val listDrinkOrder = updateToppingDrinkOrderUseCase(
                    _state.value.listDrinkOrder,
                    _state.value.indexUpdateOrderDrink,
                    event.isSelect,
                    event.index,
                    event.drink
                )
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateCountDrinkEvent -> {
                val listDrinkOrder = updateCountDrinkOrderUseCase(
                    _state.value.listDrinkOrder,
                    _state.value.indexUpdateOrderDrink,
                    event.quantity
                )
                _state.value = _state.value.copy(_listDrinkOrder = listDrinkOrder)
                updateSubTotal()
                checkVoucherAvailability()
                updateTotalPayment()
            }

            is OrderEvent.UpdateVoucherEvent -> {
                _state.value = _state.value.copy(_voucher = event.voucher)
                _state.value.voucher?.let {
                    viewModelScope.launch {
                        _eventFlow.emit(ShowToast("Đã áp dụng Voucher"))
                    }
                    val updateUseVoucherResult = updateUseVoucherUseCase(
                        it.value,
                        _state.value.subTotal,
                        _state.value.shippingFee,
                        it.freeShip
                    )
                    _state.value = _state.value.copy(
                        _disCount = updateUseVoucherResult.discount,
                        _shippingFee = updateUseVoucherResult.shippingFee
                    )
                    if (_state.value.voucher?.freeShip == false) {
                        updateDeliveryFee()
                    }
                    updateTotalPayment()
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
                val updateUseBeanResult =
                    updateUseBeanUseCase(event.useBean, _state.value.currentBean)
                _state.value = _state.value.copy(
                    _useBean = updateUseBeanResult.useBean,
                    _disCount = updateUseBeanResult.discount,
                    _voucher = if (updateUseBeanResult.useBean) null else _state.value.voucher,
                    _showDialog = if (!updateUseBeanResult.useBean) false else _state.value.showDialog
                )
                viewModelScope.launch {
                    _eventFlow.emit(ShowToast(updateUseBeanResult.toastMessage))
                }
                _state.value = _state.value.copy(
                    _useBean = event.useBean
                )
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