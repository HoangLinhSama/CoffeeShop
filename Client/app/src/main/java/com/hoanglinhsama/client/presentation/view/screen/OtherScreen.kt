package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.zxing.BarcodeFormat
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.util.rememberQrCodeImage
import com.hoanglinhsama.client.presentation.viewmodel.event.OtherEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OtherState

@Composable
fun OtherScreen(
    state: OtherState,
    event: (OtherEvent) -> Unit,
    onUtilitiesFeatureItemClick: (Int) -> Unit,
    onSupportFeatureItemClick: (Int) -> Unit,
    onAccountFeatureItemClick: (Int) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowUser, columnUtilities, columnSupport, columnAccount) = createRefs()
        Row(
            modifier = Modifier
                .background(Color.White)
                .constrainAs(rowUser) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .padding(
                        top = Dimens.mediumMargin,
                        start = Dimens.mediumMargin,
                        bottom = Dimens.mediumMargin
                    )
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(70.dp),
                    model = state.user?.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = (state.user?.firstName + " ${state.user?.lastName}"),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeTitle
                ),
                color = DarkCharcoal2,
                modifier = Modifier
                    .padding(start = Dimens.mediumMargin)
                    .weight(1f)
            )
            state.user?.phone?.let {
                val qrCode =
                    rememberQrCodeImage(state.user.phone, BarcodeFormat.QR_CODE, 512, 512, true)
                if (qrCode != null) {
                    Image(
                        bitmap = qrCode,
                        modifier = Modifier
                            .size(90.dp)
                            .padding(start = Dimens.mediumMargin, end = Dimens.mediumMargin)
                            .clickable {
                                OtherEvent.ShowQrCodeEvent
                            },
                        contentDescription = null
                    )
                }
            }
        }
        Column(modifier = Modifier.constrainAs(columnUtilities) {
            top.linkTo(rowUser.bottom, Dimens.mediumMargin)
            start.linkTo(parent.start, Dimens.mediumMargin)
            end.linkTo(parent.end, Dimens.mediumMargin)
            width = Dimension.fillToConstraints
        }) {
            Text(
                text = "Tiện ích",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeSubTitle
                ),
                color = DarkCharcoal2
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = Dimens.smallMargin)
            ) {
                state.listUtilitiesFeatureItem?.let { listUtilitiesFeatureItem ->
                    repeat(listUtilitiesFeatureItem.size) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(Dimens.roundedCornerSize / 2))
                                .background(Color.White)
                                .clickable {
                                    onUtilitiesFeatureItemClick(it)
                                }
                                .wrapContentHeight()
                                .weight(1 / listUtilitiesFeatureItem.size.toFloat())) {
                            Icon(
                                painterResource(listUtilitiesFeatureItem[it].icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(
                                        top = Dimens.smallMargin,
                                        start = Dimens.smallMargin,
                                        end = Dimens.smallMargin
                                    )
                                    .size(24.dp),
                                tint = listUtilitiesFeatureItem[it].color!!
                            )
                            Text(
                                text = listUtilitiesFeatureItem[it].title,
                                modifier = Modifier.padding(
                                    top = Dimens.smallMargin,
                                    start = Dimens.smallMargin,
                                    end = Dimens.smallMargin,
                                    bottom = Dimens.smallMargin
                                ),
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        if (it != listUtilitiesFeatureItem.size - 1) {
                            Spacer(modifier = Modifier.size(Dimens.smallMargin / 2))
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.constrainAs(columnSupport) {
            top.linkTo(columnUtilities.bottom, Dimens.mediumMargin)
            start.linkTo(parent.start, Dimens.mediumMargin)
            end.linkTo(parent.end, Dimens.mediumMargin)
            width = Dimension.fillToConstraints
        }) {
            Text(
                text = "Hỗ trợ",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeSubTitle
                ),
                color = DarkCharcoal2
            )
            Column(
                modifier = Modifier
                    .padding(top = Dimens.smallMargin)
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .background(Color.White)
            ) {
                state.listSupportFeatureItem?.let { listSupportFeatureItem ->
                    repeat(listSupportFeatureItem.size) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    onSupportFeatureItemClick(it)
                                }
                                .padding(
                                    top = Dimens.smallMargin,
                                    start = Dimens.smallMargin,
                                    end = Dimens.smallMargin,
                                    bottom = Dimens.smallMargin
                                )) {
                            Icon(
                                painter = painterResource(listSupportFeatureItem[it].icon),
                                contentDescription = null,
                                tint = CopperRed,
                                modifier = Modifier
                                    .padding(end = Dimens.smallMargin)
                                    .size(24.dp)
                            )
                            Text(
                                text = listSupportFeatureItem[it].title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                color = DarkCharcoal2
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            listSupportFeatureItem[it].trailing?.invoke()
                        }
                        if (it != listSupportFeatureItem.size - 1) {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .background(GainsBoro)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.constrainAs(columnAccount) {
            start.linkTo(parent.start, Dimens.mediumMargin)
            end.linkTo(parent.end, Dimens.mediumMargin)
            bottom.linkTo(parent.bottom, 110.dp)
            width = Dimension.fillToConstraints
        }) {
            Text(
                text = "Tài khoản",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeSubTitle
                ), color = DarkCharcoal2
            )
            state.listAccountFeatureItem?.let { listAccountFeatureItem ->
                Column(
                    modifier = Modifier
                        .padding(top = Dimens.smallMargin)
                        .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                        .background(Color.White)
                ) {
                    repeat(listAccountFeatureItem.size) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    onAccountFeatureItemClick(it)
                                }
                                .padding(
                                    top = Dimens.smallMargin,
                                    start = Dimens.smallMargin,
                                    end = Dimens.smallMargin,
                                    bottom = Dimens.smallMargin
                                )) {
                            Icon(
                                painter = painterResource(listAccountFeatureItem[it].icon),
                                contentDescription = null,
                                tint = CopperRed,
                                modifier = Modifier
                                    .padding(end = Dimens.smallMargin)
                                    .size(24.dp)
                            )
                            Text(
                                text = listAccountFeatureItem[it].title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                color = DarkCharcoal2
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            listAccountFeatureItem[it].trailing?.invoke()
                        }
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .background(GainsBoro)
                                .fillMaxWidth()
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                event(OtherEvent.LogoutEvent)
                            }
                            .padding(
                                top = Dimens.smallMargin,
                                start = Dimens.smallMargin,
                                end = Dimens.smallMargin,
                                bottom = Dimens.smallMargin
                            )) {
                        Icon(
                            painter = painterResource(R.drawable.ic_log_out),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .size(24.dp)
                        )
                        Text(
                            text = "Đăng xuất",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            color = Color.Red
                        )
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
        val listUtilitiesFeatureItem = listOf(
            FeatureItem(R.drawable.ic_order, "Lịch sử đơn hàng", Color.Red, null),
            FeatureItem(R.drawable.ic_devide, "Quản lý đăng nhập", Color.Blue, null)
        )
        val listAccountFeatureItem = listOf(
            FeatureItem(R.drawable.ic_profile, "Thông tin cá nhân", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_mark, "Địa chỉ đã lưu", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_language, "Cài đặt", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
        )
        val listSupportFeatureItem = listOf(
            FeatureItem(R.drawable.ic_mail, "Liên hệ góp ý", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
            FeatureItem(R.drawable.ic_info, "Về chúng tôi", null, {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DarkCharcoal2
                )
            }),
        )
        OtherScreen(
            OtherState(
                _listAccountFeatureItem = listAccountFeatureItem,
                _listUtilitiesFeatureItem = listUtilitiesFeatureItem,
                _listSupportFeatureItem = listSupportFeatureItem,
                _user = User(1, "Linh", "Hoàng", "+84968674274", "", "", 3, 0, 0)
            ), {}, {}, {}) {}
    }
}