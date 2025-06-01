package com.hoanglinhsama.client.presentation.view.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.OrderStatus
import com.hoanglinhsama.client.domain.model.Status
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.widget.DrinkOrderCard
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderStatusScreen(
    activity: Activity? = null,
    orderId: Int,
    openFromOrderHistory: Boolean,
    state: OrderStatusState,
    event: (OrderStatusEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        event(OrderStatusEvent.GetOrderStatusEvent(orderId))
    }
    LaunchedEffect(state.orderStatus) {
        if (!state.hasLaunchedPayment) {
            state.orderStatus?.let {
                if (it.listStatus.lastOrNull()?.name == "Chờ thanh toán") {
                    event(
                        OrderStatusEvent.PaymentEvent(
                            it.methodPayment,
                            orderId,
                            activity!!
                        ) { status, dataMessage ->
                            if (status) {
                                event(
                                    OrderStatusEvent.UpdateStatePaymentEvent(
                                        dataMessage,
                                        true,
                                        orderId,
                                        23
                                    ) { status, message ->
                                        if (status == "success") {
                                            onBackClick()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Đã có lỗi xảy ra: $message",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                )
                            } else {
                                Toast.makeText(context, dataMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
    val formatterMoney = DecimalFormat("#,###")
    val formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowTitle, columnPayment, barDivide5, columnPrice, barDivide4, barDivide3, columnDrinkOrder, columnInfoOrder, barDivide2, rowAction, barDivide1, rowStatusOrder, constraintLayout1, boxTrackDelivery) = createRefs()
        Row(
            modifier = Modifier.constrainAs(rowTitle) {
                top.linkTo(parent.top, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            state.orderStatus?.let {
                if (!openFromOrderHistory && it.listStatus[it.listStatus.size - 1].name != "Chờ thanh toán") {
                    Icon(
                        painterResource(R.drawable.ic_home),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onBackClick()
                            }
                            .size(24.dp))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Trạng thái đơn hàng",
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
                .verticalScroll(rememberScrollState())) {
            Box(
                modifier = Modifier.constrainAs(boxTrackDelivery) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }) {
                state.orderStatus?.let {
                    AsyncImage(
                        model = it.listStatus[it.listStatus.size - 1].image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        error = painterResource(
                            R.drawable.img_not_found
                        ),
                        modifier = Modifier.aspectRatio(1f)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(
                            top = Dimens.mediumMargin,
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                        .clip(RoundedCornerShape(Dimens.roundedCornerSize / 2))
                        .background(GainsBoro)
                        .align(Alignment.TopEnd)
                        .size(Dimens.smallIcon * 2)
                        .clickable {
                            event(OrderStatusEvent.TrackDeliveryEvent)
                        }) {
                    Icon(
                        painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = CopperRed,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .constrainAs(rowStatusOrder) {
                        top.linkTo(boxTrackDelivery.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .clickable(enabled = openFromOrderHistory) {
                        event(OrderStatusEvent.ShowProcessStatusOrderEvent)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                state.orderStatus?.listStatus?.let {
                    val time = it[it.size - 1].dateTime.format(
                        formatterTime
                    )
                    Text(
                        text = time,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                        ),
                        color = DarkCharcoal2,
                        modifier = Modifier
                            .padding(end = Dimens.mediumMargin)
                            .weight(0.4f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(end = if (openFromOrderHistory) Dimens.mediumMargin else 0.dp)
                    ) {
                        Text(
                            text = it[it.size - 1].name,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                            color = CopperRed,
                        )
                        Text(
                            text = it[it.size - 1].description,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                            color = SpanishGray,
                        )
                    }
                }
                if (openFromOrderHistory) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DarkCharcoal2
                    )
                }
            }
            Box(
                modifier = Modifier
                    .constrainAs(barDivide1) {
                        top.linkTo(rowStatusOrder.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.smallMargin)
                    .background(GainsBoro)
                    .fillMaxWidth())
            Row(modifier = Modifier.constrainAs(rowAction) {
                top.linkTo(barDivide1.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }, verticalAlignment = Alignment.CenterVertically) {
                state.orderStatus?.listStatus?.let {
                    val listStatus = it.map {
                        it.name
                    }
                    if ((!listStatus.contains("Đã tìm thấy tài xế") && state.orderStatus.isDelivery == true) || (state.orderStatus.isDelivery == false && !listStatus.contains(
                            "Đang pha chế"
                        ))
                    ) {
                        Button(
                            onClick = {
                                event(OrderStatusEvent.CancelOrderEvent(orderId))
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(Dimens.buttonHeight)
                                .padding(end = Dimens.mediumMargin)
                                .weight(0.5f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CopperRed
                            )
                        ) {
                            Text(
                                text = "Hủy đơn",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                            )
                        }
                    }
                    if (!listStatus.contains("Đã thanh toán")) {
                        Button(
                            onClick = {
                                event(OrderStatusEvent.ChangeMethodPaymentEvent(state.orderStatus.methodPayment))
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(Dimens.buttonHeight)
                                .weight(0.6f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GainsBoro
                            )
                        ) {
                            Text(
                                text = "Đổi phương thức thanh toán",
                                color = CopperRed,
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .constrainAs(barDivide2) {
                        top.linkTo(rowAction.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.smallMargin)
                    .background(GainsBoro)
                    .fillMaxWidth())
            Column(modifier = Modifier.constrainAs(columnInfoOrder) {
                top.linkTo(barDivide2.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = "Thông tin đơn hàng",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                )
                state.orderStatus?.let {
                    if (it.isDelivery) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = Dimens.smallMargin)
                        ) {
                            Column(modifier = Modifier.weight(0.5f)) {
                                Text(
                                    text = "Tên người nhận",
                                    color = DarkCharcoal2,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                                it.name?.let {
                                    Text(
                                        text = it,
                                        color = SpanishGray,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .height(30.dp)
                                    .background(GainsBoro)
                                    .width(1.dp)
                                    .padding(start = Dimens.smallMargin, end = Dimens.smallMargin)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(start = Dimens.mediumMargin)
                            ) {
                                Text(
                                    text = "Số điện thoại",
                                    color = DarkCharcoal2,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                                it.phone?.let {
                                    Text(
                                        text = it,
                                        color = SpanishGray,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = Dimens.smallMargin)
                                .height(1.dp)
                                .background(GainsBoro)
                                .fillMaxWidth()
                        )
                        Text(
                            modifier = Modifier.padding(top = Dimens.smallMargin),
                            text = "Giao đến",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                        )
                        it.address?.let {
                            Text(
                                text = it,
                                color = SpanishGray,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                            )
                        }
                    } else {
                        Text(
                            modifier = Modifier.padding(top = Dimens.smallMargin),
                            text = "Đến lấy tại",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                        )
                        it.shopName?.let { shopName ->
                            it.shopAddress?.let { shopAddress ->
                                Text(
                                    text = shopName + "\n" + shopAddress,
                                    color = SpanishGray,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = Dimens.smallMargin)
                            .height(1.dp)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                }
                Text(
                    modifier = Modifier.padding(top = Dimens.smallMargin),
                    text = "Trạng thái thanh toán",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
                state.orderStatus?.listStatus?.let {
                    val listStatus = it.map { it.name }
                    Text(
                        text = if (listStatus.contains("Đã thanh toán")) "Đã thanh toán" else "Chưa thanh toán",
                        color = CopperRed,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
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
                    Column(modifier = Modifier.weight(0.5f)) {
                        Text(
                            text = "Mã đơn hàng",
                            color = DarkCharcoal2,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                        )
                        Text(
                            text = "$orderId",
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    if (state.paymentBillId != null) {
                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .background(GainsBoro)
                                .width(1.dp)
                                .padding(start = Dimens.smallMargin, end = Dimens.smallMargin)
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = Dimens.mediumMargin)
                                .weight(0.5f)
                        ) {
                            Text(
                                text = "Mã thanh toán",
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                            )
                            Text(
                                text = state.paymentBillId,
                                color = SpanishGray,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .constrainAs(barDivide3) {
                        top.linkTo(columnInfoOrder.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.smallMargin)
                    .background(GainsBoro)
                    .fillMaxWidth())
            Column(modifier = Modifier.constrainAs(columnDrinkOrder) {
                top.linkTo(barDivide3.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = "Sản phẩm đã chọn",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                )
                Column(
                    modifier = Modifier.padding(top = Dimens.smallMargin)
                ) {
                    state.orderStatus?.listDrinkOrder?.let { listDrinkOrder ->
                        repeat(listDrinkOrder.size) { index ->
                            DrinkOrderCard(
                                false,
                                Cultured,
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                listDrinkOrder[index],
                                -1,
                                index,
                                {},
                                {}) {}
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
                    .constrainAs(barDivide4) {
                        top.linkTo(columnDrinkOrder.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.smallMargin)
                    .background(GainsBoro)
                    .fillMaxWidth())
            Column(modifier = Modifier.constrainAs(columnPrice) {
                top.linkTo(barDivide4.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = "Tổng cộng",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = Dimens.smallMargin)
                ) {
                    Text(
                        text = "Thành tiền",
                        modifier = Modifier
                            .padding(end = Dimens.mediumMargin)
                            .weight(1f),
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                    )
                    Text(text = state.orderStatus?.let { formatterMoney.format(it.subTotal) }
                        .toString() + " đ",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal))
                }
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
                        text = "Phí giao hàng",
                        modifier = Modifier
                            .padding(end = Dimens.mediumMargin)
                            .weight(1f),
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                    )
                    Text(text = state.orderStatus?.let { formatterMoney.format(it.deliveryFee) }
                        .toString() + " đ",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal))
                }
                Box(
                    modifier = Modifier
                        .padding(top = Dimens.smallMargin)
                        .height(1.dp)
                        .background(GainsBoro)
                        .fillMaxWidth()
                )
                if (state.orderStatus?.voucherName != null || state.orderStatus?.quantityBeanUse != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = Dimens.smallMargin)
                    ) {
                        val text =
                            if (state.orderStatus.quantityBeanUse != null) "Đổi ${state.orderStatus.quantityBeanUse} bean" else state.orderStatus.voucherName
                        Text(
                            text = text!!,
                            modifier = Modifier
                                .padding(end = Dimens.mediumMargin)
                                .weight(1f),
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                        )
                        state.orderStatus.discount?.let {
                            Text(
                                text = "-" + formatterMoney.format(it).toString() + " đ",
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = Dimens.smallMargin)
                            .height(1.dp)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = Dimens.smallMargin)
                ) {
                    Text(
                        text = "Số tiền thanh toán",
                        modifier = Modifier
                            .padding(end = Dimens.mediumMargin)
                            .weight(1f),
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                    )
                    Text(text = state.orderStatus?.let { formatterMoney.format(it.totalPrice) }
                        .toString() + " đ",
                        color = CopperRed,
                        style = MaterialTheme.typography.labelMedium)
                }
            }
            Box(
                modifier = Modifier
                    .constrainAs(barDivide5) {
                        top.linkTo(columnPrice.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.smallMargin)
                    .background(GainsBoro)
                    .fillMaxWidth())
            Column(
                modifier = Modifier
                    .constrainAs(columnPayment) {
                        top.linkTo(barDivide5.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        bottom.linkTo(parent.bottom, 90.dp)
                        width = Dimension.fillToConstraints
                    }) {
                Text(
                    text = "Thanh toán",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        top = Dimens.smallMargin, bottom = Dimens.smallMargin
                    )
                ) {
                    Text(
                        text = "Phương thức thanh toán",
                        modifier = Modifier
                            .padding(end = Dimens.mediumMargin)
                            .weight(1f),
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                    )
                    state.orderStatus?.let {
                        Text(
                            text = it.methodPayment,
                            color = CopperRed,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
fun OrderStatusScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val drinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
        val orderStatus = OrderStatus(
            true,
            listOf(
                Status(
                    "Tạo đơn hàng",
                    "Đã gửi thông tin đơn hàng. Nếu sau 10 phút chưa nhận được xác nhận, vui lòng liên hệ với chúng tôi",
                    "",
                    LocalDateTime.of(2025, 5, 25, 12, 30, 0)
                ), Status(
                    "Xác nhận đơn",
                    "Đơn hàng của bạn vừa được xác nhận tự động",
                    "",
                    LocalDateTime.of(2025, 5, 25, 12, 30, 30)
                )
            ),
            "Linh Hoàng",
            "+84968674274",
            "63",
            "Quận 12, Hồ Chí Minh",
            listOf(drinkOrder, drinkOrder),
            null,
            null,
            _subTotal = 118000F,
            _deliveryFee = 0F,
            _discount = 0F,
            _totalPrice = 118000F,
            "Tiền mặt",
            null,
            "Free ship đơn 30K"
        )
        OrderStatusScreen(null, 1, false, OrderStatusState(orderStatus, "ZLPCFS170125052025"), {}) {

        }
    }
}