package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkSlateGray
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.R
import java.text.DecimalFormat

@Composable
fun PromotionCard(
    modifier: Modifier = Modifier,
    voucher: Voucher,
    pageSize: Int,
    selectedPage: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = voucher.picture,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(
                R.drawable.img_not_found
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(
                Dimens.smallMargin
            )
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = voucher.name + if (voucher.freeShip) " + Freeship" else "",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 25.sp),
                    color = Color.White,
                    modifier = Modifier.weight(0.6f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
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
                            Icons.Outlined.DateRange, contentDescription = null, tint = Color.White
                        )
                        Text(
                            text = (voucher.startDate + " - " + voucher.expirationDate),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Áp dụng cho ${voucher.categoryDrink.joinToString(", ")}" + if (voucher.conditions > 10) " với đơn từ ${voucher.conditions / 1000} K" else " với đơn từ ${voucher.conditions} món trở lên",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        ),
                        color = Color.White,
                        maxLines = 4
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PagerIndicator(pageSize = pageSize, selectedPage = selectedPage)
        }
    }
}

@Composable
fun DrinkCard(modifier: Modifier = Modifier, drink: Drink) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = Dimens.mediumMargin,
                    bottomStart = Dimens.mediumMargin
                )
            )
        ) {
            AsyncImage(
                model = drink.picture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                error = painterResource(
                    R.drawable.img_not_found
                )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (drink.star != null) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = CopperRed,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = drink.star?.toString() ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                        color = Color.White
                    )
                }
            }
        }
        Text(
            text = drink.name,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
            color = DarkCharcoal2,
            modifier = Modifier.padding(
                start = Dimens.smallMargin,
                top = Dimens.smallMargin,
                end = Dimens.smallMargin
            )
        )
        Row(
            modifier = Modifier.padding(Dimens.smallMargin),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formatter = DecimalFormat("#,###")
            Text(
                text = (drink.priceSize?.get("Nhỏ")
                    ?: drink.priceSize?.get("Vừa")
                    ?: drink.priceSize?.get("Lớn")
                    ?: drink.priceSize?.get(" ")
                        )?.let { formatter.format(it) + " đ" } ?: "",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkSlateGray
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

val voucher = Voucher(
    "01.07", "31.07", "Giảm 30K", 50, listOf("Trà sữa", "Cafe"), "", true
)

@Preview(showBackground = true)
@Composable
fun PromotionCardPreview() {
    ClientTheme(dynamicColor = false) {
        PromotionCard(Modifier.height(140.dp), voucher, 3, 1) {}
    }
}

val priceSize = mapOf<String, Int>("Vừa" to 30000, "Lớn" to 40000, "Nhỏ" to 20000)
val toppingPrice = mapOf<String, Int>("Bánh Flan" to 10000)
val drink = Drink(
    "Cà Phê Sữa Đá", priceSize, " ", 4.9F, "", toppingPrice
)

@Preview
@Composable
fun DrinkCardPreview() {
    ClientTheme(dynamicColor = false) {
        DrinkCard(
            modifier = Modifier
                .width(149.dp)
                .wrapContentHeight(), drink
        )
    }
}