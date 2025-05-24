package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.viewmodel.TabRowItem
import kotlinx.coroutines.flow.Flow

data class VoucherState(
    private val _listTab: List<TabRowItem>? = null,
    private val _selectedTabIndex: Int = 0,
    private val _voucherCode: String = "",
    private val _itemsVoucher: Flow<PagingData<Voucher>>? = null,
    private val _showBottomSheet: Boolean = false,
    private val _currentVoucherClick: Voucher? = null,
    private val _showDialog: Boolean = false,
) {
    val listTab = _listTab
    val selectedTabIndex = _selectedTabIndex
    val voucherCode = _voucherCode
    val itemsVoucher = _itemsVoucher
    val showBottomSheet = _showBottomSheet
    val currentVoucherClick = _currentVoucherClick
    val showDialog = _showDialog
}

