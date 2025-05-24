package com.hoanglinhsama.client.presentation.view.ui.anim

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = colorResource(id = R.color.philippine_gray).copy(alpha = alpha))
}

@Composable
@Preview(showBackground = true)
fun ShimmerEffectPreview() {
    ClientTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            Column(
                modifier = Modifier.padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                )
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .height(Dimens.mediumMargin)
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.size(Dimens.mediumMargin))
                }
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = true, onClick = {})
            .background(Color.Black.copy(alpha = 0.3f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = CopperRed,
            strokeWidth = 4.dp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingOverlayPreview() {
    ClientTheme(dynamicColor = false) {
        LoadingOverlay()
    }
}