package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.Orderr
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent

data class OrderState(
    private val _listOrder: List<Orderr>? = null,
    private val _isDelivery: Boolean = true,
    private val _bottomSheet: BottomSheetContent? = null,
    private val _listInformation: List<String>? = null,
    private val _showBottomSheet: Boolean = false,
    private val _listTextFieldFocus: List<Boolean> = List(3) { false },
    private val _addressShop: String = "Vui lòng chọn quán",
) {
    val listOrder = _listOrder
    val isDelivery = _isDelivery
    val bottomSheet = _bottomSheet
    val listInformation = _listInformation
    val showBottomSheet = _showBottomSheet
    val listTextFieldFocus = _listTextFieldFocus
    val addressShop = _addressShop
}
