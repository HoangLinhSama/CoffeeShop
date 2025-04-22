package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.viewmodel.FeatureItem
import com.hoanglinhsama.client.presentation.viewmodel.event.OtherEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OtherState

@Composable
fun OtherScreen(
    state: OtherState,
    event: (OtherEvent) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowUser, barDivide, columnFeature) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(rowUser) {
                    top.linkTo(parent.top, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.clip(CircleShape)
            ) {
                AsyncImage(
                    modifier = Modifier.size(70.dp),
                    model = state.user?.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.padding(start = Dimens.mediumMargin))
            Text(
                text = (state.user?.firstName + " ${state.user?.lastName}"),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeTitle
                ),
                color = DarkCharcoal2
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(barDivide) {
                    top.linkTo(rowUser.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .padding(0.dp)
                .height(1.dp)
                .background(GainsBoro)
        )
        Column(modifier = Modifier.constrainAs(columnFeature) {
            start.linkTo(parent.start, Dimens.mediumMargin)
            end.linkTo(parent.end, Dimens.mediumMargin)
            bottom.linkTo(parent.bottom, Dimens.mediumMargin)
            width = Dimension.fillToConstraints
        }) {
            state.listFeatureItem?.let { listFeatureItem ->
                repeat(listFeatureItem.size) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            event(OtherEvent.FeatureItemClickEvent(it))
                        }) {
                        Icon(
                            painter = painterResource(listFeatureItem[it].icon),
                            contentDescription = null,
                            tint = if (it != listFeatureItem.size - 1) CopperRed else Color.Red,
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .size(24.dp)
                        )
                        Text(
                            text = listFeatureItem[it].title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = Dimens.sizeSubtitle
                            ),
                            color = if (it != listFeatureItem.size - 1) DarkCharcoal2 else Color.Red
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        listFeatureItem[it].trailing.invoke()
                    }
                    if (it != listFeatureItem.size - 1) {
                        Spacer(modifier = Modifier.size(Dimens.mediumMargin))
                    } else {
                        Spacer(modifier = Modifier.size(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OtherScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val listFeatureItem = listOf(
            FeatureItem(R.drawable.ic_profile, "Thông tin cá nhân", {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_order, "Lịch sử đơn hàng", {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_language, "Ngôn ngữ", {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tiếng Việt",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
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
            FeatureItem(R.drawable.ic_dark_mode, "Dark Mode", {
                Switch(
                    checked = true, onCheckedChange = {},
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
            FeatureItem(R.drawable.ic_log_out, "Đăng xuất", {})
        )
        OtherScreen(
            OtherState(
                _listFeatureItem = listFeatureItem,
                _user = User(1, "Linh", "Hoàng", "", "", "")
            )
        ) {

        }
    }
}