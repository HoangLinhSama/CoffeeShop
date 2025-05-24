package com.hoanglinhsama.client.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class FeatureItem(
    private val _icon: Int,
    private val _title: String,
    private val _color: Color? = null,
    private val _trailing: (@Composable () -> Unit)?,
) {
    val icon = _icon
    val title = _title
    val color = _color
    val trailing = _trailing
}