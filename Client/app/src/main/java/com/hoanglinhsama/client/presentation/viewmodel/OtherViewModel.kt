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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
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
        val listFeatureItem = listOf(
            FeatureItem(R.drawable.ic_profile, "Thông tin cá nhân", {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_order, "Lịch sử đơn hàng", {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_language, "Ngôn ngữ", {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = state.value.language,
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(end = Dimens.smallMargin)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DarkCharcoal2
                    )
                }
            }),
            FeatureItem(R.drawable.ic_dark_mode, "Dark Mode", {
                Switch(
                    checked = state.value.isDarkMode, onCheckedChange = {
                        _state.value = _state.value.copy(_isDarkMode = it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = CopperRed,
                        uncheckedTrackColor = GainsBoro,
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }),
            FeatureItem(R.drawable.ic_log_out, "Đăng xuất", {})
        )
        _state.value = _state.value.copy(_listFeatureItem = listFeatureItem)
    }

    fun onEvent(event: OtherEvent) {
        when (event) {
            is OtherEvent.FeatureItemClickEvent -> {
                when (event.index) {
                    0 -> {

                    }

                    1 -> {

                    }

                    2 -> {

                    }

                    3 -> {

                    }

                    4 -> {
                        viewModelScope.launch {
                            logOutUseCase()
                        }
                    }
                }
            }
        }
    }
}

data class FeatureItem(
    private val _icon: Int,
    private val _title: String,
    private val _trailing: @Composable () -> Unit,
) {
    val icon = _icon
    val title = _title
    val trailing = _trailing
}