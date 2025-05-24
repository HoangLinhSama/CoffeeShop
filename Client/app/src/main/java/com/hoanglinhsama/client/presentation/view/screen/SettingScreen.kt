package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.presentation.view.ui.theme.*
import com.hoanglinhsama.client.presentation.viewmodel.event.SettingEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.SettingState

@Composable
fun SettingScreen(
    state: SettingState,
    onEvent: (SettingEvent) -> Unit,
    onSettingFeatureItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin,
                    bottom = Dimens.mediumMargin
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Cài đặt",
                color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                )
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .background(Color.White)
        ) {
            state.listSettingFeatureItem?.let { listSettingFeatureItem ->
                listSettingFeatureItem.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                onSettingFeatureItemClick(index)
                            }
                            .padding(horizontal = Dimens.mediumMargin),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = CopperRed,
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .size(24.dp)
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            color = DarkCharcoal2
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        item.trailing?.let { trailing ->
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .wrapContentWidth()
                                    .align(Alignment.CenterVertically),
                                contentAlignment = Alignment.Center
                            ) {
                                trailing()
                            }
                        }
                    }

                    if (index != listSettingFeatureItem.lastIndex) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GainsBoro)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val listSettingFeatureItem = listOf(
            FeatureItem(R.drawable.ic_fingerprint, "Vân tay", null, {
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    modifier = Modifier.scale(0.75f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = CopperRed,
                        uncheckedTrackColor = GainsBoro,
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }),
            FeatureItem(R.drawable.ic_language, "Ngôn ngữ", null, {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tiếng Việt",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(end = Dimens.smallMargin)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DarkCharcoal2
                    )
                }
            }),
            FeatureItem(R.drawable.ic_dark_mode, "Dark Mode", null, {
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    modifier = Modifier.scale(0.75f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = CopperRed,
                        uncheckedTrackColor = GainsBoro,
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            })
        )
        SettingScreen(
            state = SettingState(_listSettingFeatureItem = listSettingFeatureItem),
            onEvent = {},
            onSettingFeatureItemClick = {},
            onBackClick = {}
        )
    }
}
