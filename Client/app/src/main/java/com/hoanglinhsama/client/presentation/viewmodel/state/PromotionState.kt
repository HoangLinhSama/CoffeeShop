package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

data class PromotionState(
    private val _currentBean: Int? = null,
    private val _requiredBean: Int? = null,
    private val _listFeatureItem: List<FeatureItem>? = null,
    private val _itemsVoucher: Flow<PagingData<Voucher>>? = null,
) {
    val currentBean = _currentBean
    val requireBean = _requiredBean
    val listFeatureItem = _listFeatureItem
    val itemsVoucher = _itemsVoucher
}