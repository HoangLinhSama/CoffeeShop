package com.hoanglinhsama.client.presentation.view.widget

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.LightCopperRed
import kotlinx.coroutines.delay

@Composable
fun OtpCard(
    modifier: Modifier = Modifier,
    context: Context?,
    shouldStartCountdown: Boolean,
    sizeOTP: Int,
    phoneNumber: String,
    timeRemaining: Long,
    listCharacterOtp: List<String>,
    onResendingOtp: () -> Unit,
    onTimeRemaining: (Long) -> Unit,
    onAuthentication: (String) -> Unit,
    onValueChange: (Int, String) -> Unit,
) {
    val focusRequesters = remember { List(sizeOTP) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .padding(
                top = Dimens.mediumMargin,
                start = Dimens.smallMargin,
                end = Dimens.smallMargin
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Xác nhận mã OTP",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle),
            color = DarkCharcoal2,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Một mã xác thực gồm $sizeOTP số đã được gửi đến Zalo hoặc số điện thoại $phoneNumber",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = DarkCharcoal2,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Nhập mã để tiếp tục",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = DarkCharcoal2,
            modifier = Modifier.padding(bottom = Dimens.smallMargin)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.mediumMargin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(sizeOTP) {
                TextField(
                    value = listCharacterOtp[it],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            onValueChange(it, newValue)
                            if (newValue.isNotEmpty() && it < sizeOTP - 1) {
                                focusRequesters[it + 1].requestFocus()
                            } else if (newValue.isEmpty() && it > 0) {
                                focusRequesters[it - 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                        .aspectRatio(1f)
                        .border(
                            width = 1.dp,
                            color = if (!listCharacterOtp[it].isEmpty()) CopperRed else GainsBoro,
                            shape = RoundedCornerShape(Dimens.roundedCornerSize)
                        )
                        .focusable()
                        .focusRequester(focusRequesters[it]),
                    textStyle = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = DarkCharcoal2,
                        unfocusedTextColor = DarkCharcoal2,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CopperRed,
                        focusedContainerColor = LightCopperRed,
                        unfocusedContainerColor = GainsBoro
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ), keyboardActions = KeyboardActions(
                        onDone = {
                            if (listCharacterOtp.all { it.isNotEmpty() }) {
                                focusManager.clearFocus()
                                onAuthentication(listCharacterOtp.joinToString(""))
                            }
                        }
                    )
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin)
        ) {
            Text(
                text = "Bạn không nhận được mã,",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkCharcoal2,
            )
            if (shouldStartCountdown) {
                LaunchedEffect(key1 = timeRemaining) {
                    if (timeRemaining > 0) {
                        delay(1000L)
                        onTimeRemaining(timeRemaining - 1)
                    }
                }
            }
            Text(
                text = if (timeRemaining != 0L) " Gửi lại sau $timeRemaining s" else " Gửi lại",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = CopperRed,
                modifier = Modifier.clickable {
                    if (timeRemaining == 0L) {
                        onTimeRemaining(60L)
                        onResendingOtp()
                        Toast.makeText(context, "Mã OTP mới sẽ sớm được gửi", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtpCardPreview() {
    ClientTheme(dynamicColor = false) {
        OtpCard(
            Modifier,
            null,
            true,
            6,
            "0908334746",
            60,
            listOf("4", "9", "0", "3", "7", "6"),
            {},
            {},
            {}) { index, newValue -> }
    }
}