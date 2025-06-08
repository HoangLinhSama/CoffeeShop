package com.hoanglinhsama.client.domain.usecase.main

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.viewmodel.common.VoucherHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReceiveVoucherUseCase @Inject constructor() {
    operator fun invoke(scope: CoroutineScope): Flow<PagingData<Voucher>> {
        return (VoucherHolder.voucher as Flow<PagingData<Voucher>>).cachedIn(scope)
    }

    fun getVoucherByType(scope: CoroutineScope, typeOrder: String): Flow<PagingData<Voucher>> {
        return VoucherHolder.voucher
            .filterNotNull()
            .map { pagingData ->
                pagingData.filter { voucher ->
                    voucher.type == typeOrder
                }
            }.cachedIn(scope)
    }
}