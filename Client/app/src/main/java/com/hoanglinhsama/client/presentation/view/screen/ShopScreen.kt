package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import com.hoanglinhsama.client.presentation.view.widget.SearchBar
import com.hoanglinhsama.client.presentation.view.widget.ShopCard
import com.hoanglinhsama.client.presentation.view.widget.ShopCardShimmerEffect
import com.hoanglinhsama.client.presentation.viewmodel.event.ShopEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.ShopState
import kotlinx.coroutines.flow.flowOf

@Composable
fun ShopScreen(
    state: ShopState,
    event: (ShopEvent) -> Unit,
    onMapClick: () -> Unit,
    onShopClick: () -> Unit,
) {
    val itemsShop = state.itemsShop?.collectAsLazyPagingItems()
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (constrainLayout1, constrainLayout2, rowSearchBar, lazyRowShop) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .constrainAs(constrainLayout1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentHeight()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(rowSearchBar) {
                    top.linkTo(parent.top, 30.dp)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }) {
                SearchBar(
                    Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    {},
                    state.searchShop.toString(),
                    GainsBoro,
                    DarkCharcoal2,
                    "Tìm kiếm cửa hàng",
                    false,
                    {
                        event(ShopEvent.OnFilterClickEvent)
                    },
                    {
                        event(ShopEvent.OnSearchClickEvent(state.searchShop.toString()))
                    }) {
                    event(ShopEvent.UpdateSearchShopEvent(it))
                }
                Icon(
                    painterResource(R.drawable.ic_map),
                    contentDescription = null,
                    tint = CopperRed,
                    modifier = Modifier
                        .padding(start = Dimens.mediumMargin)
                        .size(24.dp)
                        .clickable {
                            onMapClick()
                        },
                )
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .background(GainsBoro)
                .constrainAs(constrainLayout2) {
                    top.linkTo(constrainLayout1.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxHeight()) {
            Box(modifier = Modifier.constrainAs(lazyRowShop) {
                top.linkTo(parent.top, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                itemsShop?.let {
                    if (handlePagingResult(it, Modifier.fillMaxWidth()) {
                            Column {
                                repeat(5) {
                                    ShopCardShimmerEffect(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(140.dp)
                                    )
                                    if (it < 4) {
                                        Spacer(Modifier.size(Dimens.mediumMargin))
                                    }
                                }
                            }
                        }) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                            contentPadding = PaddingValues(
                                bottom = 250.dp
                            )
                        ) {
                            items(itemsShop.itemCount) {
                                itemsShop[it]?.let { shop ->
                                    ShopCard(Modifier.height(140.dp), shop) {
                                        onShopClick()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShopScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val shop = Shop(
            "HCM Nguyễn Ảnh Thủ",
            "",
            "93/5 Nguyễn Ảnh Thủ, Huyện Hóc Môn, Hồ Chí Minh, Việt Nam",
            "0968674279",
            "07:00 - 21:30"
        )
        val listShop = listOf(shop, shop, shop)
        val mockShopPagingData = PagingData.from(listShop)
        ShopScreen(ShopState(_itemsShop = flowOf(mockShopPagingData)), {}, {}) {}
    }
}
