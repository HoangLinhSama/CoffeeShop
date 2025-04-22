package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hoanglinhsama.client.domain.model.Onboarding
import com.hoanglinhsama.client.presentation.view.ui.theme.ChineseBlack
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal1
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray

@Composable
fun OnBoardingPage(
    onboarding: Onboarding,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(ChineseBlack, DarkCharcoal1),
                start = Offset.Zero,
                end = Offset(0F, 1000F),
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            onboarding.image,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                )
                .aspectRatio(1f)
                .fillMaxWidth()
        )
        Text(
            text = onboarding.title,
            color = Cultured,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 34.sp),
            modifier = Modifier.padding(
                top = Dimens.mediumMargin,
                start = Dimens.mediumMargin,
                end = Dimens.mediumMargin
            )
        )
        Spacer(modifier = Modifier.size(Dimens.mediumMargin))
        Text(
            text = onboarding.description,
            textAlign = TextAlign.Center,
            color = SpanishGray,
            modifier = Modifier.padding(
                start = Dimens.mediumMargin,
                end = Dimens.mediumMargin
            ),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
        )
        Spacer(modifier = Modifier.size(Dimens.mediumMargin))
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    val onboarding = Onboarding(
        "",
        "Khám phá hương vị yêu thích",
        "Từ cà phê đậm đà, trà xanh thanh mát đến trà sữa béo ngậy,.. chúng tôi có tất cả để bạn lựa chọn"
    )
    ClientTheme(dynamicColor = false) {
        OnBoardingPage(onboarding, Modifier.wrapContentHeight())
    }
}
