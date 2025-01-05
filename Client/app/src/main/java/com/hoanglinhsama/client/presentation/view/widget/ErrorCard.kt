package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ErrorCard(error: LoadState.Error, modifier: Modifier = Modifier) {
    var message by remember { mutableStateOf(parseErrorMessage(error = error)) }
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    ErrorContent(modifier, alphaAnimation, message)
}

@Composable
fun ErrorContent(modifier: Modifier, alphaAnim: Float, message: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_network_error),
            contentDescription = null,
            modifier = Modifier
                .alpha(alphaAnim)
                .size(96.dp)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server không phản hồi"
        }

        is ConnectException -> {
            "Không có kết nối Internet"
        }

        is Exception -> {
            when (error.error.message) {
                "fail: no data found" -> "Không có dữ liệu"
                else -> "Lỗi không xác định"
            }
        }

        else -> {
            "Lỗi không xác định"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorCardPreview() {
    ClientTheme(dynamicColor = false) {
        ErrorContent(
            Modifier.fillMaxWidth(), 0.3f, "Không có kết nối Internet"
        )
    }
}