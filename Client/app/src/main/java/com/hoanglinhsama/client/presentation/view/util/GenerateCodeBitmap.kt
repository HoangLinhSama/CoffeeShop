package com.hoanglinhsama.client.presentation.view.util

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun generateQRCodeBitmap(
    content: String,
    format: BarcodeFormat,
    width: Int,
    height: Int,
    isTransparentBackground: Boolean = false,
): Bitmap {
    val hints = mapOf(
        EncodeHintType.CHARACTER_SET to "UTF-8",
    )
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        content,
        format,
        width,
        height,
        hints
    )

    val bitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] =
                if (bitMatrix[x, y]) Color.BLACK else if (isTransparentBackground) Color.TRANSPARENT else Color.WHITE
        }
    }
    return bitmap
}

@Composable
fun rememberQrCodeImage(
    phoneNumber: String,
    format: BarcodeFormat,
    width: Int,
    height: Int,
    isTransparentBackground: Boolean,
): ImageBitmap? {
    var qrImage by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(phoneNumber) {
        withContext(Dispatchers.Default) {
            val bitmap =
                generateQRCodeBitmap(phoneNumber, format, width, height, isTransparentBackground)
            qrImage = bitmap.asImageBitmap()
        }
    }
    return qrImage
}
