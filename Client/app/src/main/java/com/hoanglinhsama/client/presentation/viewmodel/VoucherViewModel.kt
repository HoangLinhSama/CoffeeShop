package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.presentation.viewmodel.common.VoucherHolder
import com.hoanglinhsama.client.presentation.viewmodel.event.VoucherEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.VoucherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(VoucherState())
    val state = _state

    init {
        initListTabRow()
    }

    private fun receiveInfo(typeOrder: String) {
        getVoucher(typeOrder)
    }

    private fun getVoucher(typeOrder: String) {
        val itemsVoucher = VoucherHolder.voucher
            .filterNotNull()
            .map { pagingData ->
                pagingData.filter { voucher ->
                    voucher.type == typeOrder
                }
            }.cachedIn(viewModelScope)
        _state.value =
            _state.value.copy(_itemsVoucher = itemsVoucher)
    }

    private fun initListTabRow() {
        val listTabRow = listOf(
            TabRowItem("Voucher", R.drawable.ic_voucher),
            TabRowItem("Coffee Bean", R.drawable.ic_coffee_bean)
        )
        _state.value = _state.value.copy(_listTab = listTabRow)
    }

    // TODO ("Deploy events on VoucherScreen)
    fun onEvent(event: VoucherEvent) {
        when (event) {
            is VoucherEvent.ApplyVoucherCodeEvent -> {

            }

            is VoucherEvent.UpdateVoucherCodeEvent -> {
                _state.value = _state.value.copy(_voucherCode = event.voucherCode)
            }

            is VoucherEvent.ScanQRCodeEvent -> {

            }

            is VoucherEvent.UpdateSelectedTabIndexEvent -> {
                _state.value = _state.value.copy(_selectedTabIndex = event.selectedTabIndex)
            }

            is VoucherEvent.UpdateShowBottomSheetEvent -> {
                _state.value = _state.value.copy(_showBottomSheet = event.showBottomSheet)
            }

            is VoucherEvent.ShowBottomSheetEvent -> {
                _state.value =
                    _state.value.copy(_currentVoucherClick = event.voucher, _showBottomSheet = true)
            }

            is VoucherEvent.UpdateShowDialog -> {
                _state.value = _state.value.copy(_showDialog = event.showDialog)
            }

            is VoucherEvent.ReceiveInfoEvent -> {
                receiveInfo(event.typeOrder)
            }
        }
    }
}

data class TabRowItem(val title: String, val icon: Int)