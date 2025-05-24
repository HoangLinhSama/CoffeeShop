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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.viewmodel.event.SettingEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(SettingState())
    val state = _state

    init {
        initDataFeatureItem()
    }

    private fun initDataFeatureItem() {
        val listSettingFeatureItem = listOf(
            FeatureItem(R.drawable.ic_fingerprint, "Vân tay", null, {
                Switch(
                    checked = state.value.useFingerPrint, onCheckedChange = {
                        _state.value = _state.value.copy(_useFingerPrint = it)
                    },
                    modifier = Modifier.scale(0.75f),
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
            FeatureItem(R.drawable.ic_language, "Ngôn ngữ", null, {
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
            FeatureItem(R.drawable.ic_dark_mode, "Dark Mode", null, {
                Switch(
                    checked = state.value.isDarkMode, onCheckedChange = {
                        _state.value = _state.value.copy(_isDarkMode = it)
                    },
                    modifier = Modifier.scale(0.75f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = CopperRed,
                        uncheckedTrackColor = GainsBoro,
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            })
        )
        _state.value = _state.value.copy(_listSettingFeatureItem = listSettingFeatureItem)
    }

    fun onEvent(event: SettingEvent) {

    }
}