package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetRequiredBeanUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.domain.usecase.main.ReceiveVoucherUseCase
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.viewmodel.state.PromotionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val getRequiredBeanUseCase: GetRequiredBeanUseCase,
    private val receiveVoucherUseCase: ReceiveVoucherUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(PromotionState())
    val state = _state

    init {
        initDataFeatureItem()
        getVoucher()
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getPhoneUseCase().collect {
                if (it != "") {
                    getUserUseCase(it).collect {
                        if (it is Result.Success) {
                            _state.value = _state.value.copy(
                                _requiredBean = it.data.collectedBean,
                                _currentBean = it.data.currentBean
                            )
                        }
                    }
                    getRequiredBeanUseCase(it).collect {
                        if (it is Result.Success) {
                            _state.value = _state.value.copy(
                                _requiredBean = it.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getVoucher() {
        val itemsVoucher = receiveVoucherUseCase(viewModelScope)
        _state.value = _state.value.copy(_itemsVoucher = itemsVoucher)
    }

    private fun initDataFeatureItem() {
        val listFeatureItem = listOf(
            FeatureItem(R.drawable.ic_membership, "Hạng thành viên", Color.Yellow, null),
            FeatureItem(R.drawable.ic_gift_box, "Đổi bean", Color.Red, null),
            FeatureItem(R.drawable.ic_coffee_bean, "Lịch sử bean", CopperRed, null),
            FeatureItem(R.drawable.ic_benefit, "Quyền lơi của bạn", Color.Blue, null)
        )
        _state.value = _state.value.copy(_listFeatureItem = listFeatureItem)
    }
}
