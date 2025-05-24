package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.domain.usecase.main.LogOutUseCase
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.viewmodel.event.OtherEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OtherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(OtherState())
    val state = _state

    init {
        initDataFeatureItem()
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getPhoneUseCase().collect {
                if (it != "") {
                    getUserUseCase(it).collect {
                        if (it is Result.Success) {
                            _state.value = _state.value.copy(_user = it.data)
                        }
                    }
                }
            }
        }
    }

    private fun initDataFeatureItem() {
        val listUtilitiesFeatureItem = listOf(
            FeatureItem(R.drawable.ic_order, "Lịch sử đơn hàng", Color.Red, null),
            FeatureItem(R.drawable.ic_devide, "Quản lý đăng nhập", Color.Blue, null)
        )
        val listAccountFeatureItem = listOf(
            FeatureItem(R.drawable.ic_profile, "Thông tin cá nhân", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_mark, "Địa chỉ đã lưu", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_language, "Cài đặt", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
        )
        val listSupportFeatureItem = listOf(
            FeatureItem(R.drawable.ic_mail, "Liên hệ góp ý", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_info, "Về chúng tôi", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
        )
        _state.value = _state.value.copy(
            _listAccountFeatureItem = listAccountFeatureItem,
            _listUtilitiesFeatureItem = listUtilitiesFeatureItem,
            _listSupportFeatureItem = listSupportFeatureItem,
        )
    }

    fun onEvent(event: OtherEvent) {
        when (event) {
            is OtherEvent.LogoutEvent -> {
                viewModelScope.launch {
                    logOutUseCase()
                }
            }

            OtherEvent.ShowQrCodeEvent -> {

            }
        }
    }
}