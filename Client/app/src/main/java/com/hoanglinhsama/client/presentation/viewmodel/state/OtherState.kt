package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.User

data class OtherState(
    private val _user: User? = null,
    private val _listFeatureItem: List<FeatureItem>? = null,
    private val _language: String = "Tiếng Việt",
    private val _isDarkMode: Boolean = false,
) {
    val user = _user
    val listFeatureItem = _listFeatureItem
    val language = _language
    val isDarkMode = _isDarkMode
}
