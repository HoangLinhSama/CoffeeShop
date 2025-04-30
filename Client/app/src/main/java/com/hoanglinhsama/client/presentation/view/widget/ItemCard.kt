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
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkSlateGray
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import java.text.DecimalFormat

@Composable
fun PromotionCard(
    modifier: Modifier = Modifier,
    voucher: Voucher,
    pageSize: Int,
    selectedPage: Int,
    onVoucherClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .clickable(onClick = onVoucherClick),
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
fun DrinkCard(
    modifier: Modifier = Modifier,
    drink: Drink,
    onDrinkClick: (Drink) -> Unit,
    onQuickOrderClick: (Drink) -> Unit,
) {
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
                    .clickable {
                        onDrinkClick(drink)
                    },
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
                        text = drink.star.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                        color = Color.Black
                    )
                }
            }
        }
        Text(
            text = drink.name,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
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
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onQuickOrderClick(drink)
                    },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Icon Quick Order",
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
fun ShopCard(modifier: Modifier = Modifier, shop: Shop, onShopClick: () -> Unit) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
            .background(Color.White)
            .clickable {
                onShopClick()
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = shop.picture,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(
                R.drawable.img_not_found
            ),
            modifier = Modifier
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(
                        topEnd = Dimens.roundedCornerSize,
                        bottomEnd = Dimens.roundedCornerSize
                    )
                )

        )
        Column(
            modifier = Modifier
                .padding(
                    top = Dimens.smallMargin,
                    bottom = Dimens.smallMargin,
                    start = Dimens.smallMargin,
                    end = Dimens.smallMargin
                )
                .weight(1f)
        ) {
            Text(
                text = shop.name,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                color = DarkCharcoal2,
            )
            Text(
                text = shop.address,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkCharcoal2,
                modifier = Modifier.padding(top = Dimens.smallMargin)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DrinkOrderCard(modifier: Modifier = Modifier, drinkOrder: DrinkOrder) {
    val formatter = DecimalFormat("#,###")
    Row(
        modifier = modifier.background(Cultured), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = Dimens.smallMargin,
                    bottom = Dimens.smallMargin,
                    start = Dimens.mediumMargin,
                )
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = Dimens.smallMargin / 2)
            ) {
                Text(
                    text = "${drinkOrder.count}x ",
                    style = MaterialTheme.typography.labelMedium,
                    color = DarkCharcoal2
                )
                Text(
                    text = drinkOrder.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = DarkCharcoal2
                )
            }
            if (drinkOrder.listTopping != null) {
                repeat(drinkOrder.listTopping.size) {
                    Text(
                        text = drinkOrder.listTopping[it],
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = SpanishGray,
                    )
                }
            }
            if (drinkOrder.note != "") {
                Text(
                    text = drinkOrder.note,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = SpanishGray,
                    modifier = Modifier.padding(top = Dimens.smallMargin / 2)
                )
            }
        }
        if (drinkOrder.size != null) {
            Text(
                text = drinkOrder.size,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = SpanishGray,
                modifier = Modifier.padding(start = Dimens.smallMargin)
            )
        }
        Text(
            text = drinkOrder.price.let { formatter.format(it) } + "đ",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal
            ),
            color = DarkCharcoal2,
            modifier = Modifier.padding(
                top = Dimens.smallMargin,
                bottom = Dimens.smallMargin,
                start = Dimens.smallMargin,
                end = Dimens.mediumMargin
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkOrderCardPreview() {
    ClientTheme(dynamicColor = false) {
        val drinkOrder = DrinkOrder(
            "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F
        )
        DrinkOrderCard(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(), drinkOrder
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopCardPreview() {
    ClientTheme(dynamicColor = false) {
        val shop = Shop(
            1,
            "HCM Nguyễn Ảnh Thủ",
            "",
            "93/5 Nguyễn Ảnh Thủ, Huyện Hóc Môn, Hồ Chí Minh, Việt Nam",
            "",
            "07:00 - 21:30"
        )
        ShopCard(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth(),
            shop
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionCardPreview() {
    ClientTheme(dynamicColor = false) {
        val voucher = Voucher(
            "01.07", "31.07", "Giảm 30K", 50, listOf("Trà sữa", "Cafe"), "", true
        )
        PromotionCard(Modifier.height(140.dp), voucher, 3, 1) {}
    }
}

val priceSize = mapOf<String, Int>("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
val toppingPrice = mapOf<String, Int>(
    "Shot Espresso" to 10000,
    "Trân châu trắng" to 10000,
    "Sốt Caramel" to 10000
)
val drink = Drink(
    1, "Bạc Xỉu", priceSize, "", 5F, "", toppingPrice, 1
)

@Preview(showBackground = true)
@Composable
fun DrinkCardPreview() {
    ClientTheme(dynamicColor = false) {
        DrinkCard(
            modifier = Modifier
                .width(149.dp)
                .wrapContentHeight(), drink, {}) {}
    }
}