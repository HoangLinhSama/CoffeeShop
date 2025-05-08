package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.domain.model.Voucher

sealed class VoucherEvent {
    data class ApplyVoucherCodeEvent(val voucherCode: String) : VoucherEvent()
    data class UpdateVoucherCodeEvent(val voucherCode: String) : VoucherEvent()
    object ScanQRCodeEvent : VoucherEvent()
    data class UpdateSelectedTabIndexEvent(val selectedTabIndex: Int) : VoucherEvent()
    data class UpdateShowBottomSheetEvent(val showBottomSheet: Boolean) : VoucherEvent()
    data class ShowBottomSheetEvent(val voucher: Voucher) : VoucherEvent()
    data class UpdateShowDialog(val showDialog: Boolean) : VoucherEvent()
    object UseBeanEvent : VoucherEvent()
    data class ReceiveInfoEvent(val typeOrder: String, val coffeeBean: Int) : VoucherEvent()
}