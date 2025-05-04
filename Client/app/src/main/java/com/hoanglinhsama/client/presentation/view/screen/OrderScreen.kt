package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetUpdateInfoDelivery
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetUpdateTempOrder
import com.hoanglinhsama.client.presentation.view.widget.DrinkOrderCard
import com.hoanglinhsama.client.presentation.view.widget.EmptyCart
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.*
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderState
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun OrderScreen(
    state: OrderState,
    event: (OrderEvent) -> Unit,
    onBackClick: () -> Unit,
    onShopSelect: () -> Unit,
    onAddMoreDrinkClick: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val formatter = DecimalFormat("#,###")
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (emptyCard, constraintLayout1, barDivide1, barDivide2, barDivide3, rowTitle, columnSelectedDrink, rowOrderType, columnDeliveryInformation, columnTotalPrice) = createRefs()
        Row(
            modifier = Modifier.constrainAs(rowTitle) {
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
                text = "Đơn hàng",
                color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        ConstraintLayout(
            modifier = Modifier
                .constrainAs(constraintLayout1) {
                    top.linkTo(rowTitle.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
            if (state.listDrinkOrder?.isNotEmpty() == true) {
                Box(
                    modifier = Modifier
                        .constrainAs(barDivide1) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(Dimens.smallMargin)
                        .background(GainsBoro))
                Row(
                    modifier = Modifier.constrainAs(rowOrderType) {
                        top.linkTo(barDivide1.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            event(OrderTypeClickEvent(true))
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.isDelivery) CopperRed else GainsBoro
                        )
                    ) {
                        Text(
                            text = "Giao hàng",
                            color = if (state.isDelivery) Color.White else DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                        )
                    }
                    Button(
                        onClick = {
                            event(OrderTypeClickEvent(false))
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!state.isDelivery) CopperRed else GainsBoro
                        )
                    ) {
                        Text(
                            text = "Mang đi",
                            color = if (!state.isDelivery) Color.White else DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                        )
                    }
                }
                Column(modifier = Modifier.constrainAs(columnDeliveryInformation) {
                    top.linkTo(rowOrderType.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }) {
                    Text(
                        text = if (state.isDelivery) "Thông tin giao hàng" else "Tự đến lấy hàng",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                        color = DarkCharcoal2
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = Dimens.smallMargin)
                            .clickable {
                                if (state.isDelivery) {
                                    event(SelectBottomSheetShowEvent(BottomSheetContent.BottomSheetUpdateInfoDelivery))
                                }
                            }) {
                        if (state.isDelivery) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = state.listInformation?.get(0) ?: "",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = DarkCharcoal2
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = state.listInformation?.get(1).toString(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = SpanishGray,
                                    modifier = Modifier.padding(top = Dimens.smallMargin)
                                )
                            }
                        }
                        if (state.isDelivery) {
                            Text(
                                text = state.listInformation?.get(2).toString(),
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                color = SpanishGray
                            )
                        } else {
                            Text(
                                text = state.shopName,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                color = SpanishGray,
                                modifier = Modifier.clickable {
                                    onShopSelect()
                                    event(UpdateSelectModeEvent(true))
                                })
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .constrainAs(barDivide2) {
                            top.linkTo(columnDeliveryInformation.bottom, Dimens.mediumMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(Dimens.smallMargin)
                        .background(GainsBoro))
                Column(
                    modifier = Modifier.constrainAs(columnSelectedDrink) {
                        top.linkTo(barDivide2.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                            start = Dimens.mediumMargin, end = Dimens.mediumMargin
                        )
                    ) {
                        Text(
                            text = "Sản phẩm đã chọn",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = Dimens.sizeSubTitle
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "+ Thêm",
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.clickable {
                                onAddMoreDrinkClick()
                            })
                    }
                    Column(
                        modifier = Modifier.padding(top = Dimens.smallMargin)
                    ) {
                        state.listDrinkOrder.let { listDrinkOrder ->
                            repeat(listDrinkOrder.size) { index ->
                                DrinkOrderCard(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    listDrinkOrder[index],
                                    state.currentlySwipedItemId,
                                    index,
                                    { newIndex ->
                                        event(UpdateCurrentlySwipedIndexEvent(newIndex))
                                    },
                                    {
                                        event(DeleteDrinkOrderEvent(index))
                                    }) {
                                    event(UpdateTempOrderEvent(index))
                                }
                                if (index != listDrinkOrder.size - 1) {
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
                Box(
                    modifier = Modifier
                        .constrainAs(barDivide3) {
                            top.linkTo(columnSelectedDrink.bottom, Dimens.mediumMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(Dimens.smallMargin)
                        .background(GainsBoro))
                Column(modifier = Modifier.constrainAs(columnTotalPrice) {
                    top.linkTo(barDivide3.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }) {
                    Text(
                        text = "Tổng cộng",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = Dimens.sizeSubTitle
                        ),
                        modifier = Modifier.padding(
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = Dimens.smallMargin,
                            bottom = Dimens.smallMargin,
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                    ) {
                        Text(
                            text = "Thành tiền",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .weight(1f)
                        )
                        state.subTotal?.let {
                            Text(
                                text = formatter.format(it).toString() + " đ",
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = Dimens.smallMargin,
                            bottom = Dimens.smallMargin,
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                    ) {
                        Text(
                            text = "Phí giao hàng",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .weight(1f)
                        )
                        state.shippingFee?.let {
                            Text(
                                text = if (state.listInformation?.get(2) != "") formatter.format(it)
                                    .toString() + " đ" else "Chưa chọn địa chỉ giao hàng",
                                color = if (state.listInformation?.get(2) != "") DarkCharcoal2 else CopperRed,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                top = Dimens.smallMargin,
                                bottom = Dimens.smallMargin,
                                start = Dimens.mediumMargin,
                                end = Dimens.mediumMargin
                            )
                            .clickable {

                            }
                    ) {
                        Text(
                            text = "Chọn khuyến mãi/đổi bean",
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .weight(1f)
                        )
                        Icon(
                            painterResource(R.drawable.ic_arrow_right),
                            modifier = Modifier.size(12.dp),
                            contentDescription = null,
                            tint = DarkCharcoal2
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = Dimens.smallMargin,
                            bottom = Dimens.smallMargin,
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                    ) {
                        Text(
                            text = "Số tiền thanh toán",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(end = Dimens.smallMargin)
                                .weight(1f)
                        )
                        state.totalPayment?.let {
                            Text(
                                text = formatter.format(it).toString() + " đ",
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            } else {
                EmptyCart(
                    Modifier
                        .constrainAs(emptyCard) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                        .fillMaxSize(), "Đặt đồ uống ngay thôi nào") {
                    onBackClick()
                }
            }
        }
    }
    if (state.showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.imePadding(),
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = {
                event(UpdateShowBottomSheetEvent(false))
            },
            dragHandle = null,
            content = {
                when (state.bottomSheet) {
                    is BottomSheetContent.BottomSheetUpdateInfoDelivery -> {
                        state.listInformation?.let {
                            BottomSheetUpdateInfoDelivery(
                                Modifier
                                    .fillMaxWidth()
                                    .height(630.dp),
                                it,
                                state.listInfoDeliveryFocus,
                                { index, value ->
                                    event(UpdateInfoDeliveryEvent(index, value))
                                },
                                { index, isFocus ->
                                    event(FocusChangeEvent(index, isFocus))
                                }) {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        event(UpdateShowBottomSheetEvent(false))
                                    }
                                }
                            }
                        }
                    }

                    is BottomSheetContent.BottomSheetPaymentMethod -> {

                    }

                    is BottomSheetContent.BottomSheetUpdateTempOrder -> {
                        state.listDrinkOrder?.get(state.indexUpdateOrderDrink)?.let { drinkOrder ->
                            val drink =
                                state.listUpdateDrinkOrder?.find { it.id == drinkOrder.id }
                            drink?.let { drink ->
                                BottomSheetUpdateTempOrder(
                                    drink,
                                    drinkOrder,
                                    Modifier.fillMaxWidth(),
                                    state.updateNoteFocus,
                                    {
                                        event(UpdateNoteFocusChangeEvent(it))
                                    },
                                    {
                                        event(UpdateNoteEvent(it))
                                    },
                                    {
                                        event(UpdateSizeEvent(it, drink))
                                    },
                                    { index, isSelected ->
                                        event(UpdateToppingEvent(index, isSelected, drink))
                                    },
                                    {
                                        event(UpdateCountDrinkEvent(it))
                                    }) {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }.invokeOnCompletion {
                                        if (!bottomSheetState.isVisible) {
                                            event(UpdateShowBottomSheetEvent(false))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    null -> {}
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val drinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F
        )
        val listDrinkOrder = listOf<DrinkOrder>(drinkOrder, drinkOrder)
        OrderScreen(
            OrderState(
                _listDrinkOrder = listDrinkOrder, _listInformation = listOf(
                    "Linh Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh"
                ), _subTotal = 118000F, _shippingFee = 15000F, _totalPayment = 133000F
            ), {}, {}, {}) {}
    }
}


sealed class BottomSheetContent {
    object BottomSheetUpdateInfoDelivery : BottomSheetContent()
    object BottomSheetPaymentMethod : BottomSheetContent()
    object BottomSheetUpdateTempOrder : BottomSheetContent()
}