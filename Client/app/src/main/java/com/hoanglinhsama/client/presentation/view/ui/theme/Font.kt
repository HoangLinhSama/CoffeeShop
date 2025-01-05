package com.hoanglinhsama.client.presentation.view.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.hoanglinhsama.client.R

object Font {
    val soraFont = FontFamily(
        fonts = listOf(
            Font(R.font.sora_bold, FontWeight.Bold),
            Font(R.font.sora_extrabold, FontWeight.ExtraBold),
            Font(R.font.sora_extralight, FontWeight.ExtraLight),
            Font(R.font.sora_light, FontWeight.Light),
            Font(R.font.sora_medium, FontWeight.Medium),
            Font(R.font.sora_regular, FontWeight.Normal),
            Font(R.font.sora_semibold, FontWeight.SemiBold),
            Font(R.font.sora_thin, FontWeight.Thin)
        )
    )
}