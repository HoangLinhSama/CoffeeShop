package com.hoanglinhsama.client.presentation.view.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.hoanglinhsama.client.presentation.view.widget.EmptyCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.SearchBar
import com.hoanglinhsama.client.presentation.view.widget.UseBeanDialog
import com.hoanglinhsama.client.presentation.view.widget.VoucherDetailBottomSheet
import com.hoanglinhsama.client.presentation.viewmodel.TabRowItem
import com.hoanglinhsama.client.presentation.viewmodel.event.VoucherEvent
import com.hoanglinhsama.client.presentation.viewmodel.event.VoucherEvent.UpdateShowBottomSheetEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.VoucherState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoucherScreen(
    state: VoucherState,
    typeOrder: String,
    currentBean: Int,
    subTotal: Float,
    quantity: Int,
    listDrinkCategory: List<String>,
    event: (VoucherEvent) -> Unit,
    onBackClick: () -> Unit,
    onEmptyVoucher: () -> Unit,
    onUseVoucher: (Voucher) -> Unit,
    onUseBean: (Boolean) -> Unit,
) {
    LaunchedEffect(Unit) {
        event(VoucherEvent.ReceiveInfoEvent(typeOrder))
    }
    val context = LocalContext.current
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
                        itemsVoucher?.itemCount?.let {
                            if (it > 0) {
                                itemsVoucher.let { itemsVoucher ->
                                    if (handlePagingResult(
                                            itemsVoucher,
                                            Modifier
                                                .fillMaxWidth()
                                                .height(140.dp),
                                            DarkCharcoal2
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
                                                bottom = 200.dp
                                            )
                                        ) {
                                            items(count = itemsVoucher.itemCount) {
                                                itemsVoucher[it]?.let { voucher ->
                                                    PromotionCard(
                                                        Modifier
                                                            .fillMaxWidth()
                                                            .height(140.dp),
                                                        voucher,
                                                        {
                                                            event(
                                                                VoucherEvent.ShowBottomSheetEvent(
                                                                    voucher
                                                                )
                                                            )
                                                        },
                                                        null
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                EmptyCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .background(Cultured),
                                    "Không có voucher nào phù hợp"
                                ) {
                                    onEmptyVoucher()
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
                                    if (currentBean > 0) {
                                        event(VoucherEvent.UpdateShowDialog(true))
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Không đủ bean để đổi",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
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
                                    text = "$currentBean BEAN",
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
                state.currentVoucherClick?.let { voucher ->
                    VoucherDetailBottomSheet(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        voucher = voucher,
                        false
                    ) {
                        var quantityCondition by Delegates.notNull<Int>()
                        var priceCondition by Delegates.notNull<Int>()
                        val drinkCategoryCondition = listDrinkCategory.any {
                            it in voucher.categoryDrink
                        }
                        if (voucher.conditions.toString().length < 4) {
                            quantityCondition = voucher.conditions.toString().toInt()
                            if (quantity >= quantityCondition && drinkCategoryCondition) {
                                onUseVoucher(voucher)
                            } else {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        event(UpdateShowBottomSheetEvent(false))
                                    }
                                }
                                if (!drinkCategoryCondition) {
                                    Toast.makeText(
                                        context,
                                        "Voucher chỉ áp dụng cho các đồ uống thuộc loại ${voucher.categoryDrink.joinToString()}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Đơn hàng cần có đủ từ $quantityCondition ly",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } else {
                            priceCondition = voucher.conditions.toString().toInt()
                            if (subTotal >= priceCondition && drinkCategoryCondition) {
                                onUseVoucher(voucher)
                            } else {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        event(UpdateShowBottomSheetEvent(false))
                                    }
                                }
                                if (!drinkCategoryCondition) {
                                    Toast.makeText(
                                        context,
                                        "Voucher chỉ áp dụng cho các đồ uống thuộc loại ${voucher.categoryDrink.joinToString()}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Đơn hàng cần có tổng giá trị từ $priceCondition đ",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            })
    }
    if (state.showDialog) {
        UseBeanDialog("Đổi bean", "Bạn có muốn đổi $currentBean bean không ?", {
            onUseBean(true)
        }) {
            event(VoucherEvent.UpdateShowDialog(false))
        }
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
            1,
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
                _listTab = listTabRow, _itemsVoucher = flowOf(mockVoucherPagingData)
            ), "delivery", 5, 150000F, 3, listOf("Trà sữa", "Cafe"), {}, {}, {}, {}) {

        }
    }
}