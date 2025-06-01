package com.hoanglinhsama.client.domain.usecase.main

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.viewmodel.common.VoucherHolder
import javax.inject.Inject

class SendVoucherUseCase @Inject constructor() {
    operator fun invoke(voucher: PagingData<Voucher>) {
        VoucherHolder.setVoucher(voucher)
    }
}