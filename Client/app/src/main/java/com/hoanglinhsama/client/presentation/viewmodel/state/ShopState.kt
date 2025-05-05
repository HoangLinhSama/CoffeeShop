package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Shop
import kotlinx.coroutines.flow.Flow

data class ShopState(
    private val _searchShop: String = "",
    private val _itemsShop: Flow<PagingData<Shop>>? = null,
) {
    val searchShop = _searchShop
    val itemsShop = _itemsShop
}
