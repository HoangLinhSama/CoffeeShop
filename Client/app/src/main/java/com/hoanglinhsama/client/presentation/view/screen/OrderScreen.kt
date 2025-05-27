package com.hoanglinhsama.client.presentation.view.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.presentation.view.ui.anim.LoadingOverlay
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.Platinum
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.util.HandleFlowResult
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetPaymentMethod
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetUpdateInfoDelivery
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetUpdateTempOrder
import com.hoanglinhsama.client.presentation.view.widget.ConfirmInfoOrderDialog
import com.hoanglinhsama.client.presentation.view.widget.DrinkOrderCard
import com.hoanglinhsama.client.presentation.view.widget.EmptyCard
import com.hoanglinhsama.client.presentation.view.widget.UseBeanDialog
import com.hoanglinhsama.client.presentation.view.widget.VoucherDetailBottomSheet
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.DeleteDrinkOrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.FocusChangeEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.OrderTypeClickEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.ResetEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.SelectBottomSheetShowEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateCountDrinkEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateCurrentlySwipedIndexEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateInfoDeliveryEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateNoteEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateNoteFocusChangeEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdatePaymentSelectedEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateSelectModeEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateShowBottomSheetEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateSizeEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateTempOrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UpdateToppingEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent.UseVoucherLaterEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.UiEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun OrderScreen(
    eventFlow: SharedFlow<UiEvent>,
    state: OrderState,
    event: (OrderEvent) -> Unit,
    onBackClick: () -> Unit,
    onShopSelect: () -> Unit,
    onSelectPromotion: (String, Int, Float, Int, List<String>) -> Unit,
    onAddMoreDrinkClick: () -> Unit,
    onOrder: (Int) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val formatter = DecimalFormat("#,###")
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.ShowSnackBar -> {}
            }
        }
    }
    LaunchedEffect(key1 = state.orderResultFlow) {
        state.orderResultFlow?.collect {
            event(OrderEvent.UpdateResultOrder(it))
            if (it is Result.Success) {
                event(OrderEvent.UpdateOrderId(it.data) {
                    event(ResetEvent)
                    onOrder(it)
                })
            }
        }
    }
    state.resultOrder?.let {
        HandleFlowResult(it) {
            LoadingOverlay()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (emptyCard, constraintLayout1, barDivide1, barDivide2, barDivide5, barDivide3, barDivide4, rowTitle, columnSelectedDrink, rowOrderType, columnDeliveryInformation, columnPayment, columnTotalPrice) = createRefs()
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
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
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
                                        true,
                                        Color.White,
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
                                start = Dimens.mediumMargin, end = Dimens.mediumMargin
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
                                    text = if (state.listInformation?.get(2) != "") formatter.format(
                                        it
                                    ).toString() + " đ" else "Chưa chọn địa chỉ giao hàng",
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
                                    if (state.voucher == null && !state.useBean) {
                                        state.currentBean?.let {
                                            val quantity = state.listDrinkOrder.sumOf {
                                                it.count
                                            }
                                            val listDrinkCategory =
                                                state.listDrinkOrder.map { it.drinkCategory }
                                                    .distinct()
                                            state.subTotal?.let { subTotal ->
                                                onSelectPromotion(
                                                    if (state.isDelivery) "delivery" else "takeaway",
                                                    it,
                                                    subTotal,
                                                    quantity,
                                                    listDrinkCategory
                                                )
                                            }
                                        }
                                    } else if (state.useBean) {
                                        event(OrderEvent.SelectDialogShowEvent(DialogContent.UseBeanDialog))
                                    } else {
                                        event(
                                            SelectBottomSheetShowEvent(
                                                BottomSheetContent.BottomSheetVoucherUseLater
                                            )
                                        )
                                    }
                                }) {
                            Text(
                                text = if (state.voucher == null && !state.useBean) "Chọn khuyến mãi/đổi bean" else if (state.useBean) "Đổi ${state.currentBean} bean" else state.voucher!!.name,
                                color = CopperRed,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier
                                    .padding(end = Dimens.smallMargin)
                                    .weight(1f)
                            )
                            if (state.voucher == null && !state.useBean) {
                                Icon(
                                    painterResource(R.drawable.ic_arrow_right),
                                    modifier = Modifier.size(12.dp),
                                    contentDescription = null,
                                    tint = DarkCharcoal2
                                )
                            } else {
                                Text(
                                    text = "-" + formatter.format(state.disCount) + " đ",
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
                    Box(
                        modifier = Modifier
                            .constrainAs(barDivide4) {
                                top.linkTo(columnTotalPrice.bottom, Dimens.mediumMargin)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .height(Dimens.smallMargin)
                            .background(GainsBoro))
                    Column(modifier = Modifier.constrainAs(columnPayment) {
                        top.linkTo(barDivide4.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }) {
                        Text(
                            text = "Thanh toán",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = Dimens.sizeSubTitle
                            ),
                            modifier = Modifier.padding(
                                start = Dimens.mediumMargin, end = Dimens.mediumMargin
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(
                                    top = Dimens.smallMargin,
                                    start = Dimens.mediumMargin,
                                    end = Dimens.mediumMargin
                                )
                                .clickable {
                                    event(SelectBottomSheetShowEvent(BottomSheetContent.BottomSheetPaymentMethod))
                                }) {
                            state.listMethodPayment?.let {
                                Text(
                                    text = it[state.indexPaymentSelected].title,
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
                        }
                    }
                    Box(
                        modifier = Modifier
                            .constrainAs(barDivide5) {
                                top.linkTo(columnPayment.bottom, Dimens.mediumMargin)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .height(180.dp)
                            .background(GainsBoro))
                } else {
                    EmptyCard(
                        Modifier
                            .constrainAs(emptyCard) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                            .fillMaxSize()
                            .background(Cultured), "Đặt đồ uống ngay thôi nào") {
                        onBackClick()
                    }
                    event(ResetEvent)
                }
            }
        }
        if (state.listDrinkOrder?.isNotEmpty() == true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
                    .background(CopperRed)
                    .shadow(
                        elevation = 24.dp, spotColor = Platinum, ambientColor = Platinum
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = Dimens.mediumMargin,
                            start = Dimens.mediumMargin,
                            bottom = Dimens.mediumMargin
                        )
                        .weight(0.5f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (state.isDelivery) "Giao hàng" else "Mang đi",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.padding(end = Dimens.smallMargin)
                        )
                        state.listDrinkOrder.let {
                            val quantity = state.listDrinkOrder.sumOf {
                                it.count
                            }
                            Text(
                                text = "$quantity sản phẩm",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(top = Dimens.smallMargin),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.listMethodPayment?.let {
                            Text(
                                text = it[state.indexPaymentSelected].title,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier.padding(end = Dimens.smallMargin)
                            )
                        }
                        state.totalPayment?.let {
                            Text(
                                text = formatter.format(it).toString() + " đ",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
                IconButton(
                    onClick = {
                        if (state.isDelivery) {
                            state.listInformation?.let {
                                if (it[0] != "" && it[1] != "" && it[2] != "") {
                                    event(OrderEvent.SelectDialogShowEvent(DialogContent.ConfirmInfoOrderDialog))
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Vui lòng điền đầy đủ thông tin giao hàng",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            if (state.shopId != null) {
                                event(OrderEvent.SelectDialogShowEvent(DialogContent.ConfirmInfoOrderDialog))
                            } else {
                                Toast.makeText(
                                    context, "Vui lòng chọn quán", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }, modifier = Modifier
                        .padding(
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin,
                            top = Dimens.mediumMargin,
                            bottom = Dimens.mediumMargin
                        )
                        .weight(0.5f)
                        .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                        .background(Color.White)
                ) {
                    Text(
                        text = "Đặt hàng",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                        color = CopperRed
                    )
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
                        state.listMethodPayment?.let {
                            var listMethodPayment = it
                            if (!state.isDelivery) {
                                listMethodPayment = it.filter {
                                    it.title == "Tiền mặt"
                                }
                            }
                            BottomSheetPaymentMethod(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                listMethodPayment,
                                state.indexPaymentSelected
                            ) {
                                event(UpdatePaymentSelectedEvent(it))
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

                    BottomSheetContent.BottomSheetVoucherUseLater -> {
                        state.voucher?.let {
                            VoucherDetailBottomSheet(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(), it, true
                            ) {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        event(UpdateShowBottomSheetEvent(false))
                                        event(UseVoucherLaterEvent)
                                    }
                                }
                            }
                        }
                    }

                    null -> {}
                }
            })
    }
    if (state.showDialog) {
        when (state.dialog) {
            DialogContent.UseBeanDialog -> {
                UseBeanDialog(
                    "Sử dụng bean sau",
                    "Bạn có muốn sử dụng ${state.currentBean} bean sau không ?",
                    {
                        event(OrderEvent.UpdateUseBeanEvent(false))
                    }) {
                    event(OrderEvent.UpdateShowDialog(false))
                }
            }

            DialogContent.ConfirmInfoOrderDialog -> {
                val formatterVie =
                    DateTimeFormatter.ofPattern("HH:mm, EEEE, dd-MM-yyyy", Locale("vi"))
                val timeOrder = LocalDateTime.now()
                val timeDelivery = timeOrder.plusMinutes(30)
                val typeOrder = if (state.isDelivery) "Giao tận nơi" else "Tự đến lấy"
                val message =
                    if (state.isDelivery) {
                        "Đơn hàng $typeOrder sẽ được giao vào ${timeDelivery.format(formatterVie)} tại ${
                            state.listInformation?.get(
                                2
                            )
                        }"
                    } else {
                        "Đơn hàng $typeOrder sẽ được giao vào ${timeDelivery.format(formatterVie)} tại cửa hàng ${
                            state.shopName
                        }"
                    }
                ConfirmInfoOrderDialog(
                    "Xác nhận thông tin đơn hàng",
                    message,
                    {
                        event(OrderEvent.UpdateShowDialog(false))
                    }) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    state.shippingFee?.let { deliveryFee ->
                        state.subTotal?.let { subTotal ->
                            state.totalPayment?.let { totalPrice ->
                                state.listDrinkOrder?.let { listDrinkOrder ->
                                    var name = state.listInformation?.get(0)
                                    var phone = state.listInformation?.get(1)
                                    var address = state.listInformation?.get(2)
                                    if (!state.isDelivery) {
                                        name = null
                                        phone = null
                                        address = null
                                    }
                                    event(
                                        OrderEvent.SendOrderEvent(
                                            state.userId!!,
                                            name,
                                            phone,
                                            address,
                                            timeOrder.format(formatter),
                                            if (state.useBean) state.currentBean else null,
                                            state.listMethodPayment!![state.indexPaymentSelected].title,
                                            if (state.isDelivery) null else state.shopId,
                                            state.isDelivery,
                                            deliveryFee,
                                            subTotal,
                                            totalPrice,
                                            state.voucher?.id,
                                            null,
                                            listDrinkOrder
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            null -> {

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val drinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
        val listDrinkOrder = listOf<DrinkOrder>(drinkOrder, drinkOrder)
        val listMethodPayment = listOf(
            FeatureItem(R.drawable.ic_cash, "Tiền mặt", null, null),
            FeatureItem(R.drawable.ic_zalopay, "ZaloPay", null, null),
            FeatureItem(R.drawable.ic_momo, "MoMo", null, null),
            FeatureItem(R.drawable.ic_vnpay, "VNPay", null, null),
            FeatureItem(R.drawable.ic_shopeepay, "ShopeePay", null, null),
            FeatureItem(R.drawable.ic_bank_card, "Thẻ ngân hàng", null, null),
            FeatureItem(R.drawable.ic_paypal, "PayPal", null, null),
        )
        OrderScreen(
            remember { MutableSharedFlow() }, OrderState(
                _listDrinkOrder = listDrinkOrder,
                _listInformation = listOf(
                    "Linh Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh"
                ),
                _subTotal = 118000F,
                _shippingFee = 15000F,
                _totalPayment = 133000F,
                _listMethodPayment = listMethodPayment
            ), {}, {}, {}, { memberShip, currentBean, subTotal, quantity, drinkCategory -> }, {}) {

        }
    }
}


sealed class BottomSheetContent {
    object BottomSheetUpdateInfoDelivery : BottomSheetContent()
    object BottomSheetPaymentMethod : BottomSheetContent()
    object BottomSheetUpdateTempOrder : BottomSheetContent()
    object BottomSheetVoucherUseLater : BottomSheetContent()
}

sealed class DialogContent {
    object UseBeanDialog : DialogContent()
    object ConfirmInfoOrderDialog : DialogContent()
}