package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.MilkChocolate
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MilkChocolate, CopperRed),
                    start = Offset.Zero,
                    end = Offset(0F, 1000F),
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(R.drawable.ic_app),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(
                    100.dp
                )
        )
        Spacer(modifier = Modifier.size(Dimens.mediumMargin))
        Text(
            text = "Coffee Shop",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 24.sp),
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ClientTheme(dynamicColor = false) {
        SplashScreen()
    }
}