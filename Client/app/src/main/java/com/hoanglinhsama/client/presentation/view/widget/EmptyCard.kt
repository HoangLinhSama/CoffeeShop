package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens

@Composable
fun EmptyCart(modifier: Modifier, message: String, onClick: () -> Unit) {
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
    EmptyContent(modifier, alphaAnimation, message, onClick)
}

@Composable
fun EmptyContent(modifier: Modifier, alphaAnim: Float, message: String, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .background(Color.White)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_empty_cart),
            contentDescription = null,
            modifier = Modifier
                .alpha(alphaAnim)
                .size(130.dp)
        )
        Text(
            modifier = Modifier
                .padding(5.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCartPreview() {
    ClientTheme(dynamicColor = false) {
        EmptyContent(Modifier.fillMaxSize(), 0.3f, "Đặt đồ uống ngay thôi !") {

        }
    }
}