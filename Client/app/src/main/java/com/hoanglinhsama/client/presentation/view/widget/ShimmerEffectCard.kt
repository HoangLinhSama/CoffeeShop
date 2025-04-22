package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.anim.shimmerEffect
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens

@Composable
fun PromotionCardShimmerEffect(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_image),
            contentDescription = null,
            modifier = Modifier.shimmerEffect()
        )
        Column(
            modifier = Modifier.padding(
                Dimens.smallMargin
            )
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .height(25.dp)
                        .shimmerEffect()
                )
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(start = Dimens.smallMargin)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = Dimens.smallMargin)
                    ) {
                        Icon(
                            Icons.Outlined.DateRange,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .shimmerEffect()
                        )
                    }
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .padding(bottom = 6.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DrinkCardShimmerEffect(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(size = Dimens.mediumMargin))
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = Dimens.mediumMargin,
                        bottomEnd = Dimens.mediumMargin
                    )
                )
                .aspectRatio(1f)
                .shimmerEffect()
        )
        Box(
            modifier = Modifier
                .height(30.dp)
                .padding(
                    start = Dimens.smallMargin,
                    top = Dimens.smallMargin,
                    end = Dimens.smallMargin
                )
                .fillMaxWidth()
                .shimmerEffect()
        )

        Row(
            modifier = Modifier
                .padding(Dimens.smallMargin),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(15.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(32.dp)
                    .background(CopperRed)
                    .align(Alignment.CenterVertically),
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(Dimens.roundedCornerSize)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PolicySheetShimmerEffect(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(15.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.size(Dimens.smallMargin))
        repeat(3) { timeSentence ->
            repeat(3) { timeRow ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            fraction = if (timeRow == 2) 0.5f else 1f
                        )
                        .height(15.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.size(size = if (timeSentence == 2 && timeRow == 2) 0.dp else Dimens.smallMargin / 2))
            }
        }
        Spacer(modifier = Modifier.size(Dimens.mediumMargin))
    }
}

@Preview(showBackground = true)
@Composable
fun PolicySheetShimmerEffectPreview() {
    ClientTheme(dynamicColor = false) {
        PolicySheetShimmerEffect(
            modifier = Modifier.wrapContentHeight()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionCardShimmerEffectPreview() {
    ClientTheme(dynamicColor = false) {
        PromotionCardShimmerEffect(modifier = Modifier.height(140.dp))
    }
}

@Composable
@Preview
fun DrinkCardShimmerEffectPreview() {
    ClientTheme(dynamicColor = false) {
        DrinkCardShimmerEffect(
            modifier = Modifier
                .height(240.dp)
                .width(150.dp)
        )
    }
}