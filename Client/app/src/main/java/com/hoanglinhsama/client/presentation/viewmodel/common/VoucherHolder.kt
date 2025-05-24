package com.hoanglinhsama.client.presentation.viewmodel.common

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.MutableStateFlow

object VoucherHolder {
    private val _voucher = MutableStateFlow<PagingData<Voucher>?>(null)
    val voucher = _voucher

    fun setVoucher(voucher: PagingData<Voucher>) {
        _voucher.value = voucher
    }
}