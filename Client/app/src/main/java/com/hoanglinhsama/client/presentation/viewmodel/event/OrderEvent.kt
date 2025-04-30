package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent

sealed class OrderEvent {
    data class OrderTypeClickEvent(val isDelivery: Boolean) : OrderEvent()
    data class SelectBottomSheetShowEvent(val bottomSheet: BottomSheetContent) : OrderEvent()
    data class UpdateInfoDeliveryEvent(val index: Int, val value: String) : OrderEvent()
    data class UpdateShowBottomSheetEvent(val showBottomSheet: Boolean) : OrderEvent()
    data class FocusChangeEvent(val index: Int, val isFocus: Boolean) : OrderEvent()
    data class UpdateSelectModeEvent(val isSelectMode: Boolean) : OrderEvent()
    data class UpdateSelectShopEvent(
        val shop: Shop,
        val isSelectMode: Boolean,
    ) : OrderEvent()
}