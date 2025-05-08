package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro

@Composable
fun VoucherDetailBottomSheet(
    modifier: Modifier = Modifier,
    voucher: Voucher,
    onVoucherUse: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(
                top = Dimens.mediumMargin,
                start = Dimens.mediumMargin,
                end = Dimens.mediumMargin
            )
    ) {
        Text(
            text = voucher.name,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            color = DarkCharcoal2
        )
        AsyncImage(
            model = voucher.qrCode,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(
                R.drawable.img_not_found
            ),
            modifier = Modifier
                .padding(top = Dimens.mediumMargin)
                .aspectRatio(1f)
                .background(Color.Transparent)
        )
        Text(
            text = voucher.code,
            style = MaterialTheme.typography.labelMedium,
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.mediumMargin),
            color = CopperRed
        )
        Box(
            modifier = Modifier
                .padding(top = Dimens.smallMargin)
                .height(1.dp)
                .background(GainsBoro)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = Dimens.smallMargin)
        ) {
            Text(
                text = "Ngày hết hạn: ",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.weight(1f),
                color = DarkCharcoal2
            )
            Text(
                text = voucher.expirationDate,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(start = Dimens.mediumMargin),
                color = CopperRed
            )
        }
        Box(
            modifier = Modifier
                .padding(top = Dimens.smallMargin)
                .height(1.dp)
                .background(GainsBoro)
                .fillMaxWidth()
        )
        Text(
            text = voucher.description,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(top = Dimens.smallMargin),
            color = DarkCharcoal2
        )
        Button(
            modifier = Modifier
                .padding(top = Dimens.mediumMargin, bottom = Dimens.mediumMargin)
                .fillMaxWidth()
                .height(Dimens.buttonHeight),
            shape = RoundedCornerShape(Dimens.roundedCornerSize),
            onClick = {
                onVoucherUse()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CopperRed
            )
        ) {
            Text(
                text = "Sử dụng ngay",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherDetailBottomSheetPreview() {
    ClientTheme(dynamicColor = false) {
        val voucher = Voucher(
            "TUNGBUNG30",
            "01.07",
            "31.07",
            "Giảm 30K Đơn 99K",
            "Giảm 30K cho đơn từ 99K\n" +
                    "1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\n" +
                    "2/ Áp dụng cho coffee, trà trái cây, trà sữa.\n" +
                    "3/ Không áp dụng song song các chương trình khác.\n" +
                    "4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.",
            30000F, "delivery", false, 99000,
            listOf("Trà sữa", "Cafe"),
            "", ""
        )
        VoucherDetailBottomSheet(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(), voucher
        ) {}
    }
}