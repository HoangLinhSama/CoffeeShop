package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent
import com.hoanglinhsama.client.presentation.view.screen.DialogContent
import kotlinx.coroutines.flow.Flow

data class OrderState(
    private val _listDrinkOrder: List<DrinkOrder>? = emptyList<DrinkOrder>(),
    private val _isDelivery: Boolean = true,
    private val _bottomSheet: BottomSheetContent? = null,
    private val _dialog: DialogContent? = null,
    private val _listInformation: List<String>? = null,
    private val _userId: Int? = null,
    private val _showBottomSheet: Boolean = false,
    private val _listInfoDeliveryFocus: List<Boolean> = List(3) { false },
    private val _shopName: String = "Vui lòng chọn quán",
    private val _shopScreenIsSelectMode: Boolean = false,
    private val _shopId: Int? = null,
    private val _currentlySwipedIndex: Int = -1,
    private val _updateNoteFocus: Boolean = false,
    private val _indexUpdateOrderDrink: Int = -1,
    private val _listUpdateDrinkOrder: List<Drink>? = emptyList<Drink>(),
    private val _subTotal: Float? = null,
    private val _totalPayment: Float? = null,
    private val _shippingFee: Float? = null,
    private val _disCount: Float = 0F,
    private val _currentBean: Int? = null,
    private val _voucher: Voucher? = null,
    private val _listMethodPayment: List<FeatureItem>? = null,
    private val _indexPaymentSelected: Int = 0,
    private val _useBean: Boolean = false,
    private val _showDialog: Boolean = false,
    private val _orderResultFlow: Flow<Result<Int>>? = null,
    private val _resultOrder: Result<Int>? = null,
    private val _orderId: Int? = null,
) {
    val listDrinkOrder = _listDrinkOrder
    val isDelivery = _isDelivery
    val bottomSheet = _bottomSheet
    val dialog = _dialog
    val listInformation = _listInformation
    val showBottomSheet = _showBottomSheet
    val userId = _userId
    val listInfoDeliveryFocus = _listInfoDeliveryFocus
    val shopName = _shopName
    val shopScreenIsSelectMode = _shopScreenIsSelectMode
    val shopId = _shopId
    val currentlySwipedItemId = _currentlySwipedIndex
    val updateNoteFocus = _updateNoteFocus
    val indexUpdateOrderDrink = _indexUpdateOrderDrink
    val listUpdateDrinkOrder = _listUpdateDrinkOrder
    val subTotal = _subTotal
    val totalPayment = _totalPayment
    val shippingFee = _shippingFee
    val disCount = _disCount
    val currentBean = _currentBean
    val voucher = _voucher
    val listMethodPayment = _listMethodPayment
    val indexPaymentSelected = _indexPaymentSelected
    val useBean = _useBean
    val showDialog = _showDialog
    val orderResultFlow = _orderResultFlow
    val resultOrder = _resultOrder
    val orderId = _orderId
}
