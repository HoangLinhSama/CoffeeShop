package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.screen.BottomSheetContent
import com.hoanglinhsama.client.presentation.view.screen.DialogContent

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
    data class UpdateSizeEvent(val size: String, val drink: Drink) : OrderEvent()
    data class UpdateToppingEvent(val index: Int, val isSelect: Boolean, val drink: Drink) :
        OrderEvent()

    data class UpdateCountDrinkEvent(val quantity: Int) : OrderEvent()
    data class UpdateVoucherEvent(val voucher: Voucher?) : OrderEvent()
    object ResetEvent : OrderEvent()
    data class UpdatePaymentSelectedEvent(val index: Int) : OrderEvent()
    data class UpdateUseBeanEvent(val useBean: Boolean) : OrderEvent()
    object UseVoucherLaterEvent : OrderEvent()
    data class UpdateShowDialog(val showDialog: Boolean) : OrderEvent()
    data class SelectDialogShowEvent(val dialog: DialogContent) : OrderEvent()
    data class SendOrderEvent(
        val userId: Int,
        val name: String?,
        val phone: String?,
        val address: String?,
        val dateTime: String,
        val quantityBeanUse: Int?,
        val paymentMethod: String,
        val shopId: Int?,
        val isDelivery: Boolean,
        val deliveryFee: Float,
        val subTotal: Float,
        val totalPrice: Float,
        val voucherId: Int?,
        val paymentBillId: String?,
        val listDrinkOrder: List<DrinkOrder>,
    ) : OrderEvent()

    data class UpdateResultOrder(val result: Result<Int>) : OrderEvent()
    data class UpdateOrderId(val orderId: Int, val callback: (Int) -> Unit) : OrderEvent()
}