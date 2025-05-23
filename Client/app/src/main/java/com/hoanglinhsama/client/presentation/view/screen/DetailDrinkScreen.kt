package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.LightCopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Platinum
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.viewmodel.event.DetailDrinkEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.DetailDrinkState
import java.text.DecimalFormat

@Composable
fun DetailDrinkScreen(
    drink: Drink,
    state: DetailDrinkState,
    event: (DetailDrinkEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    val formatter = DecimalFormat("#,###")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val priceSize = if (drink.priceSize!!.keys.toList()[0] == " ") drink.priceSize[" "]
        else drink.priceSize[drink.priceSize.keys.toList()[state.indexSizeSelected]]
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (columnMain, rowActionBar1, textFieldNote, columnTopping, rowSize, rowDescription, rowNamePrice, barDivide, imageDrink, rowActionBar2) = createRefs()
            Row(
                modifier = Modifier.constrainAs(rowActionBar1) {
                    top.linkTo(parent.top, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackClick()
                    })
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Chi tiết",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Column(
                modifier = Modifier
                    .constrainAs(columnMain) {
                        top.linkTo(rowActionBar1.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .verticalScroll(rememberScrollState())) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = drink.picture,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .constrainAs(imageDrink) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(Dimens.roundedCornerSize)),
                        error = painterResource(R.drawable.img_not_found))
                    Row(
                        modifier = Modifier.constrainAs(rowNamePrice) {
                            top.linkTo(imageDrink.bottom, Dimens.mediumMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = drink.name,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 20.sp),
                            color = DarkCharcoal2,
                            modifier = Modifier.weight(0.8f)
                        )
                        Text(
                            text = priceSize.let { formatter.format(it) + " đ" },
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.weight(0.2f)
                        )
                    }
                    Row(modifier = Modifier.constrainAs(rowActionBar2) {
                        top.linkTo(rowNamePrice.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, verticalAlignment = Alignment.CenterVertically) {
                        if (drink.star != null) {
                            Icon(
                                Icons.Outlined.Star,
                                contentDescription = null,
                                tint = CopperRed,
                                modifier = Modifier.size(Dimens.smallIcon)
                            )
                            Text(
                                text = drink.star.toString(),
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                                color = DarkCharcoal2
                            )
                            Text(
                                text = "(${drink.countReview} đánh giá)",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                modifier = Modifier
                                    .clickable {
                                        event(DetailDrinkEvent.ReviewClickEvent(drink.id))
                                    }
                                    .padding(start = Dimens.smallMargin))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            tint = if (state.isFavorite) CopperRed else DarkCharcoal2,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .size(Dimens.mediumMargin)
                                .clickable {
                                    event(DetailDrinkEvent.FavoriteClickEvent(!state.isFavorite))
                                })
                        Icon(
                            Icons.Outlined.Share,
                            contentDescription = null,
                            tint = DarkCharcoal2,
                            modifier = Modifier
                                .size(Dimens.mediumMargin)
                                .clickable {
                                    event(DetailDrinkEvent.ShareClickEvent)
                                })
                    }
                    Box(
                        modifier = Modifier
                            .constrainAs(barDivide) {
                                top.linkTo(rowActionBar2.bottom, Dimens.smallMargin)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(0.dp)
                            .height(1.dp)
                            .background(GainsBoro))
                    val maxLines = 3
                    Column(modifier = Modifier.constrainAs(rowDescription) {
                        top.linkTo(barDivide.bottom, Dimens.smallMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }) {
                        Text(
                            color = SpanishGray,
                            text = if (state.isExpanded) drink.description else drink.description.take(
                                maxLines * 50
                            ),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                            maxLines = if (state.isExpanded) Int.MAX_VALUE else maxLines,
                        )
                        Row {
                            if (!state.isExpanded) {
                                Text(
                                    text = "...",
                                    color = SpanishGray,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                            }
                            Text(
                                text = if (state.isExpanded) "Rút gọn" else "Xem thêm",
                                color = CopperRed,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.clickable {
                                    event(DetailDrinkEvent.ReadMoreDescriptionClickEvent(!state.isExpanded))
                                })
                        }
                    }
                    Row(
                        modifier = Modifier.constrainAs(rowSize) {
                            top.linkTo(rowDescription.bottom, Dimens.mediumMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (drink.priceSize.keys.toList()[0] != " ") {
                            repeat(drink.priceSize.size) {
                                val isSelected = state.indexSizeSelected == it
                                Box(
                                    Modifier
                                        .width(99.dp)
                                        .height(43.dp)
                                        .clip(RoundedCornerShape(Dimens.smallMargin))
                                        .background(if (isSelected) CopperRed else Color.White)
                                        .clickable {
                                            event(DetailDrinkEvent.SizeSelectedEvent(it))
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        color = if (isSelected) Color.White else DarkCharcoal2,
                                        text = drink.priceSize.keys.toList()[it],
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.constrainAs(columnTopping) {
                            top.linkTo(rowSize.bottom, Dimens.mediumMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }) {
                        if (drink.toppingPrice != null) {
                            repeat(drink.toppingPrice.size) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = state.listToppingChecked[it],
                                        onCheckedChange = { isChecked ->
                                            event(DetailDrinkEvent.ToppingCheckEvent(it, isChecked))
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = CopperRed, uncheckedColor = SpanishGray
                                        )
                                    )
                                    Text(
                                        text = drink.toppingPrice.keys.toList()[it],
                                        color = DarkCharcoal2,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    val priceTopping =
                                        drink.toppingPrice[drink.toppingPrice.keys.toList()[it]]
                                    Text(
                                        text = priceTopping.let { formatter.format(it) + " đ" },
                                        color = CopperRed,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    }
                    val focusManager = LocalFocusManager.current
                    TextField(
                        value = state.noteOrder,
                        onValueChange = {
                            event(DetailDrinkEvent.NoteOrderEvent(it))
                        },
                        modifier = Modifier
                            .constrainAs(textFieldNote) {
                                top.linkTo(columnTopping.bottom, Dimens.mediumMargin)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom, 200.dp)
                                width = Dimension.fillToConstraints
                            }
                            .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                            .border(
                                width = 1.dp,
                                color = if (state.isFocus) CopperRed else GainsBoro,
                                shape = RoundedCornerShape(Dimens.roundedCornerSize)
                            )
                            .onFocusChanged {
                                event(DetailDrinkEvent.NoteFocusEvent(it.isFocused))
                            },
                        textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                        placeholder = {
                            Text(
                                text = "Thêm ghi chú"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_note), contentDescription = null
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = DarkCharcoal2,
                            unfocusedTextColor = DarkCharcoal2,
                            focusedPlaceholderColor = SpanishGray,
                            unfocusedPlaceholderColor = SpanishGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = CopperRed,
                            focusedContainerColor = LightCopperRed,
                            unfocusedContainerColor = GainsBoro,
                            focusedLeadingIconColor = CopperRed,
                            unfocusedLeadingIconColor = SpanishGray
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            })
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = Dimens.mediumMargin, topEnd = Dimens.mediumMargin
                    )
                )
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .shadow(
                    elevation = 24.dp, spotColor = Platinum, ambientColor = Platinum
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(0.5f), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_minus),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = Dimens.mediumMargin)
                        .size(Dimens.mediumMargin)
                        .border(
                            width = 1.dp, color = GainsBoro, shape = RoundedCornerShape(3.dp)
                        )
                        .clickable {
                            if (state.countDrink > 1) event(DetailDrinkEvent.DrinkCountEvent(state.countDrink - 1))
                        },
                    tint = if (state.countDrink == 1) GainsBoro else CopperRed
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = state.countDrink.toString(),
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimens.mediumMargin)
                        .border(
                            width = 1.dp, color = GainsBoro, shape = RoundedCornerShape(3.dp)
                        )
                        .clickable {
                            event(DetailDrinkEvent.DrinkCountEvent(state.countDrink + 1))
                        },
                    tint = CopperRed
                )
            }
            Spacer(modifier = Modifier.padding(end = Dimens.mediumMargin))
            val totalToppingPrice = if (drink.toppingPrice != null) {
                state.listToppingChecked.mapIndexed { index, isChecked ->
                    if (isChecked) drink.toppingPrice.values.toList()[index] else 0
                }.sum()
            } else 0
            val totalPrice: Float =
                (priceSize!! + totalToppingPrice).times(state.countDrink).toFloat()
            IconButton(
                onClick = {
                    val size =
                        if (drink.priceSize.keys.toList()[0] == " ") null else drink.priceSize.keys.toList()[state.indexSizeSelected]
                    val listToppingChecked: List<String>? =
                        drink.toppingPrice?.keys?.toList()?.filterIndexed { index, _ ->
                            state.listToppingChecked[index]
                        }
                    event(
                        DetailDrinkEvent.OrderEvent(
                            drink.id,
                            drink.picture,
                            drink.name,
                            totalPrice,
                            size,
                            listToppingChecked,
                            drink.drinkCategory
                        )
                    )
                    event(DetailDrinkEvent.SendUpdateDrinkOrderEvent(drink))
                    onBackClick()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .background(CopperRed)
                    .weight(0.5f)
                    .height(Dimens.buttonHeight),
            ) {
                Text(
                    text = totalPrice.let { formatter.format(it) + " đ" },
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(end = Dimens.mediumMargin))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailDrinkScreenPreview() {
    val priceSize = mapOf<String, Int>("Nhỏ" to 49000, "Vừa" to 55000, "Lớn" to 65000)
    val toppingPrice = mapOf<String, Int>(
        "Shot Espresso" to 10000, "Trân châu trắng" to 10000, "Sốt Caramel" to 10000
    )
    val drink = Drink(
        1,
        "Phin Sữa Tươi Bánh Flan",
        priceSize,
        "",
        5F,
        "Tỉnh tức thì cùng cà phê Robusta pha phin đậm đà và bánh flan núng nính. Uống là tỉnh, ăn là dính, xứng đáng là highlight trong ngày của bạn.",
        toppingPrice,
        1,
        "Cafe"
    )
    ClientTheme(dynamicColor = false) {
        DetailDrinkScreen(drink, DetailDrinkState(), {}) {

        }
    }
}