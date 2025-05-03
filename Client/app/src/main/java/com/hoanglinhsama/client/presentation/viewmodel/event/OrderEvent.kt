package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.domain.model.Drink
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

    data class UpdateCurrentlySwipedIndexEvent(val newIndex: Int) : OrderEvent()
    data class DeleteDrinkOrderEvent(val index: Int) : OrderEvent()
    data class UpdateTempOrderEvent(val indexDinkOrder: Int) :
        OrderEvent()

    data class UpdateNoteFocusChangeEvent(val isFocus: Boolean) : OrderEvent()
    data class UpdateNoteEvent(val note: String) : OrderEvent()
    data class UpdateSizeEvent(val size: String, val drink:Drink) : OrderEvent()
    data class UpdateToppingEvent(val index: Int, val isSelect: Boolean, val drink: Drink) : OrderEvent()
    data class UpdateCountDrinkEvent(val quantity: Int) : OrderEvent()
}