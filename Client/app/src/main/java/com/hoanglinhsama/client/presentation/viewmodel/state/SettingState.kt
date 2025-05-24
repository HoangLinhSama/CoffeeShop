package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.FeatureItem

data class SettingState(
    private val _listSettingFeatureItem: List<FeatureItem>? = null,
    private val _language: String = "Tiếng Việt",
    private val _isDarkMode: Boolean = false,
    private val _useFingerPrint: Boolean = false,
) {
    val listSettingFeatureItem = _listSettingFeatureItem
    val language = _language
    val isDarkMode = _isDarkMode
    val useFingerPrint = _useFingerPrint
}