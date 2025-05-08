package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import com.hoanglinhsama.client.presentation.view.widget.PromotionCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.SearchBar
import com.hoanglinhsama.client.presentation.view.widget.VoucherDetailBottomSheet
import com.hoanglinhsama.client.presentation.viewmodel.TabRowItem
import com.hoanglinhsama.client.presentation.viewmodel.event.VoucherEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.VoucherEvent.UpdateShowBottomSheetEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.VoucherState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoucherScreen(
    state: VoucherState,
    event: (VoucherEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        event(VoucherEvent.ReceiveInfoEvent("delivery", 5))
    }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val itemsVoucher = state.itemsVoucher?.collectAsLazyPagingItems()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowTitle, searchBar, constraintLayout1, tabRow1) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(rowTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClick()
                    }
                    .padding(start = Dimens.mediumMargin))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(
                    top = Dimens.mediumMargin,
                    bottom = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                ),
                text = "Voucher của tôi",
                color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        ConstraintLayout(modifier = Modifier.constrainAs(constraintLayout1) {
            top.linkTo(rowTitle.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }) {
            SearchBar(
                modifier = Modifier.constrainAs(searchBar) {
                    top.linkTo(parent.top, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                },
                null,
                state.voucherCode,
                Color.White,
                DarkCharcoal2,
                "Nhập mã khuyến mãi",
                false,
                {
                    Icon(
                        painterResource(R.drawable.ic_scan),
                        contentDescription = null,
                        tint = CopperRed
                    )
                },
                null,
                {},
                {
                    event(VoucherEvent.ApplyVoucherCodeEvent(state.voucherCode))
                },
                {
                    event(VoucherEvent.UpdateVoucherCodeEvent(it))
                },
                {
                    event(VoucherEvent.ScanQRCodeEvent)
                })
            Column(modifier = Modifier.constrainAs(tabRow1) {
                top.linkTo(searchBar.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = Color.Transparent,
                    modifier = Modifier.padding(bottom = Dimens.mediumMargin)
                ) {
                    state.listTab?.forEachIndexed { index, tabRowItem ->
                        Tab(
                            selected = state.selectedTabIndex == index,
                            onClick = { event(VoucherEvent.UpdateSelectedTabIndexEvent(index)) },
                            text = {
                                Text(
                                    text = tabRowItem.title,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = if (state.selectedTabIndex == index) CopperRed else SpanishGray
                                )
                            },
                            icon = {
                                Icon(
                                    painterResource(tabRowItem.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (state.selectedTabIndex == index) CopperRed else SpanishGray
                                )
                            })
                    }
                }
                when (state.selectedTabIndex) {
                    0 -> {
                        itemsVoucher?.let { itemsVoucher ->
                            if (handlePagingResult(
                                    itemsVoucher, Modifier
                                        .fillMaxWidth()
                                        .height(140.dp), DarkCharcoal2
                                ) {
                                    Column {
                                        repeat(4) {
                                            PromotionCardShimmerEffect(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .height(140.dp)
                                            )
                                            if (it < 4) {
                                                Spacer(Modifier.size(Dimens.mediumMargin))
                                            }
                                        }
                                    }
                                }
                            ) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                                    contentPadding = PaddingValues(
                                        bottom = Dimens.mediumMargin
                                    )
                                ) {
                                    items(count = itemsVoucher.itemCount) {
                                        itemsVoucher[it]?.let { voucher ->
                                            PromotionCard(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .height(140.dp), voucher, {
                                                    event(
                                                        VoucherEvent.ShowBottomSheetEvent(
                                                            voucher
                                                        )
                                                    )
                                                }, null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    event(VoucherEvent.UpdateShowDialog(true))
                                }) {
                            Image(
                                painterResource(R.drawable.img_coffee_bean),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(70.dp)
                                    .background(Color.Transparent),
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = Dimens.mediumMargin)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Số bean hiện tại của bạn",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = DarkCharcoal2
                                )
                                Text(
                                    text = "${state.coffeeBean} BEAN",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = CopperRed,
                                    modifier = Modifier.padding(top = Dimens.smallMargin)
                                )
                            }
                        }
                    }
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
                state.currentVoucherClick?.let {
                    VoucherDetailBottomSheet(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        voucher = it,
                    ) {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                event(UpdateShowBottomSheetEvent(false))
                            }
                        }
                    }
                }
            })
    }
    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = "Đổi bean",
                )
            },
            text = {
                Text(
                    text = "Bạn có muốn đổi ${state.coffeeBean} bean không ?",
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    event(VoucherEvent.UseBeanEvent)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { event(VoucherEvent.UpdateShowDialog(false)) }) {
                    Text("Huỷ")
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val listTabRow = listOf(
            TabRowItem("Voucher", R.drawable.ic_voucher),
            TabRowItem("Coffee Bean", R.drawable.ic_coffee_bean)
        )
        val voucher = Voucher(
            "TUNGBUNG30",
            "01.07",
            "31.07",
            "Giảm 30K Đơn 99K",
            "Giảm 30K cho đơn từ 99K\n" + "1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\n" + "2/ Áp dụng cho coffee, trà trái cây, trà sữa.\n" + "3/ Không áp dụng song song các chương trình khác.\n" + "4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.",
            30000F,
            "delivery",
            false,
            99000,
            listOf("Trà sữa", "Cafe"),
            "",
            ""
        )
        val listVoucher = listOf(voucher, voucher, voucher)
        val mockVoucherPagingData = PagingData.from(listVoucher)
        VoucherScreen(
            VoucherState(
                _listTab = listTabRow, _itemsVoucher = flowOf(mockVoucherPagingData),
                _coffeeBean = 5
            ), {}) {}
    }
}