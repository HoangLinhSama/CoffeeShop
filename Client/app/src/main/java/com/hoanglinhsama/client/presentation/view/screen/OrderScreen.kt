package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.widget.BottomSheetUpdateInfoDelivery
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    state: OrderState,
    event: (OrderEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
            .verticalScroll(rememberScrollState())
            .padding(
                top = Dimens.mediumMargin,
                start = Dimens.mediumMargin,
                end = Dimens.mediumMargin
            )
    ) {
        val (rowTitle, rowOrderType, columnDeliveryInformation) = createRefs()
        Row(
            modifier = Modifier.constrainAs(rowTitle) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
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
        Row(
            modifier = Modifier.constrainAs(rowOrderType) {
                top.linkTo(rowTitle.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    event(OrderEvent.OrderTypeClickEvent(true))
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
                    event(OrderEvent.OrderTypeClickEvent(false))
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
            start.linkTo(parent.start)
            end.linkTo(parent.end)
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
                            event(OrderEvent.SelectBottomSheetShowEvent(BottomSheetContent.BottomSheetUpdateInfoDelivery))
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
                        text = state.addressShop,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                        color = SpanishGray,
                        modifier = Modifier.clickable {
                            event(OrderEvent.SelectShop)
                        }
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
                event(OrderEvent.UpdateShowBottomSheetEvent(false))
            },
            content = {
                when (state.bottomSheet) {
                    is BottomSheetContent.BottomSheetUpdateInfoDelivery -> {
                        state.listInformation?.let {
                            BottomSheetUpdateInfoDelivery(
                                Modifier
                                    .fillMaxWidth()
                                    .height(630.dp),
                                it, state.listTextFieldFocus,
                                { index, value ->
                                    event(OrderEvent.UpdateInfoDeliveryEvent(index, value))
                                }, { index, isFocus ->
                                    event(OrderEvent.FocusChangeEvent(index, isFocus))
                                }) {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        event(OrderEvent.UpdateShowBottomSheetEvent(false))
                                    }
                                }
                            }
                        }
                    }

                    is BottomSheetContent.BottomSheetPaymentMethod -> {

                    }

                    null -> {}
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    ClientTheme(dynamicColor = false) {
        OrderScreen(
            OrderState(
                _listInformation = listOf(
                    "Linh Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh"
                )
            ), {}) {}
    }
}

sealed class BottomSheetContent {
    object BottomSheetUpdateInfoDelivery : BottomSheetContent()
    object BottomSheetPaymentMethod : BottomSheetContent()
}