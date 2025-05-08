package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.anim.shimmerEffect
import com.hoanglinhsama.client.presentation.view.ui.theme.ChineseBlack
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal1
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkSlateGray
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import com.hoanglinhsama.client.presentation.view.widget.DrinkCard
import com.hoanglinhsama.client.presentation.view.widget.DrinkCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.PagerIndicator
import com.hoanglinhsama.client.presentation.view.widget.PromotionCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.SearchBar
import com.hoanglinhsama.client.presentation.viewmodel.event.HomeEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.HomeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    state: HomeState,
    event: (HomeEvent) -> Unit,
    onSearchClick: () -> Unit,
    onDrinkClick: (Drink) -> Unit,
) {
    val itemsVoucher = state.itemsVoucher?.collectAsLazyPagingItems()
    val itemsDrinkCategory = state.itemsDrinkCategory?.collectAsLazyPagingItems()
    val itemsDrink = state.itemsDrink?.collectAsLazyPagingItems()
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (constraintLayout1, constraintLayout2, rowBar, rowSearch, spacer, promotionCard) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(ChineseBlack, DarkCharcoal1),
                        start = Offset.Zero,
                        end = Offset(0F, 500F),
                    )
                )
                .constrainAs(constraintLayout1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .constrainAs(rowBar) {
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        top.linkTo(parent.top, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = { state.user?.let { event(HomeEvent.AvatarClickEvent(it)) } }
                        )
                ) {
                    AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = state.user?.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "Xin chào, " + state.user?.firstName,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { event(HomeEvent.NotificationClickEvent) }
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(Dimens.mediumMargin)
                    )
                }
                IconButton(
                    onClick = {
                        state.user?.id.let {
                            it?.let { userId ->
                                event(
                                    HomeEvent.FavoriteClickEvent(
                                        userId
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        Modifier.size(Dimens.mediumMargin),
                        tint = Color.White
                    )
                }
            }
            SearchBar(
                Modifier
                    .constrainAs(rowSearch) {
                        top.linkTo(rowBar.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints

                    }
                    .wrapContentHeight(),
                onSearchClick,
                "",
                DarkCharcoal1,
                Color.White,
                "Tìm đồ uống",
                true,
                {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(
                            Dimens.smallIcon
                        )
                    )
                },
                null,
                {}, {}, {}, null
            )
            Box(
                modifier = Modifier
                    .constrainAs(promotionCard) {
                        top.linkTo(rowSearch.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }) {
                itemsVoucher?.let { itemsVoucher ->
                    if (handlePagingResult(
                            items = itemsVoucher,
                            Modifier
                                .height(140.dp)
                                .fillMaxWidth(), Cultured
                        ) {
                            PromotionCardShimmerEffect(
                                Modifier.height(140.dp)
                            )
                        }
                    ) {
                        val autoFlipInterval = 3000L
                        var currentIndex = state.currentPromotionIndex
                        LaunchedEffect(key1 = currentIndex) {
                            delay(autoFlipInterval)
                            event(HomeEvent.PromotionAutoFlipperEvent(itemsVoucher.itemCount))
                        }
                        for (index in 0 until (itemsVoucher.itemCount)) {
                            AnimatedVisibility(
                                visible = index == currentIndex,
                                enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
                                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }),
                            ) {
                                itemsVoucher[index]?.let {
                                    PromotionCard(
                                        modifier = Modifier.height(140.dp),
                                        voucher = it,
                                        onVoucherClick = {
                                            event(HomeEvent.VoucherClickEvent)
                                        }) {
                                        PagerIndicator(
                                            pageSize = itemsVoucher.itemCount,
                                            selectedPage = currentIndex
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.constrainAs(spacer) {
                top.linkTo(promotionCard.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
        ConstraintLayout(
            modifier = Modifier
                .background(Cultured)
                .constrainAs(constraintLayout2) {
                    top.linkTo(constraintLayout1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize()) {
            val (drinkCategoryLazyRow, drinkLazyVerticalGrid) = createRefs()
            Box(modifier = Modifier.constrainAs(drinkCategoryLazyRow) {
                top.linkTo(spacer.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
                itemsDrinkCategory?.let { itemsDrinkCategory ->
                    if (handlePagingResult(itemsDrinkCategory, color = Cultured) {
                            Row(
                                modifier = Modifier
                                    .padding(top = Dimens.mediumMargin)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                repeat(3) {
                                    Box(
                                        modifier = Modifier
                                            .width(109.dp)
                                            .height(38.dp)
                                            .clip(RoundedCornerShape(size = Dimens.smallMargin))
                                            .background(color = Color.White)
                                            .shimmerEffect()

                                    )
                                }
                            }
                        }) {
                        LazyRow(
                            modifier = Modifier.padding(top = Dimens.mediumMargin),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(count = itemsDrinkCategory.itemCount) {
                                val isSelected = state.selectedDrinkCategory == it
                                Button(
                                    onClick = {
                                        event(
                                            HomeEvent.DrinkCategoryClickEvent(
                                                itemsDrinkCategory[it]!!.name,
                                                it
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = if (isSelected) Color.White else DarkSlateGray,
                                        containerColor = if (isSelected) CopperRed else Color.White
                                    ),
                                    shape = RoundedCornerShape(size = Dimens.smallMargin),
                                    modifier = Modifier
                                        .width(109.dp)
                                        .height(38.dp)
                                ) {
                                    Text(
                                        text = itemsDrinkCategory[it]!!.name,
                                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .constrainAs(drinkLazyVerticalGrid) {
                        top.linkTo(drinkCategoryLazyRow.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }) {
                itemsDrink?.let { itemsDrink ->
                    if (handlePagingResult(
                            itemsDrink,
                            Modifier.aspectRatio(1f), DarkCharcoal2
                        ) {
                            Column {
                                repeat(2) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        repeat(2) {
                                            DrinkCardShimmerEffect(
                                                modifier = Modifier
                                                    .height(250.dp)
                                                    .width(160.dp)
                                            )
                                        }
                                    }
                                    if (it < 2) {
                                        Spacer(Modifier.size(Dimens.mediumMargin))
                                    }
                                }
                            }
                        }
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                            horizontalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                            contentPadding = PaddingValues(
                                bottom = 530.dp
                            )
                        ) {
                            items(itemsDrink.itemCount) {
                                DrinkCard(
                                    drink = itemsDrink[it]!!, onDrinkClick = {
                                        onDrinkClick(it)
                                    }
                                ) {
                                    event(HomeEvent.QuickOderClickEvent(it))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val listVoucher = listOf(
        Voucher(
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
    )
    val priceSize = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
    val toppingPrice = mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000)
    val drink = Drink(1, "Bạc Sỉu", priceSize, "", 5F, "", toppingPrice, 1)
    val listDrink = listOf(drink, drink, drink)
    val drinkCategory = DrinkCategory("Cafe")
    val listDrinkCategory = listOf(drinkCategory, drinkCategory, drinkCategory)

    val mockVoucherPagingData = PagingData.from(listVoucher)
    val mockDrinkPagingData = PagingData.from(listDrink)
    val mockCategoryPagingData = PagingData.from(listDrinkCategory)

    ClientTheme(dynamicColor = false) {
        HomeScreen(
            state = HomeState(
                flowOf(mockVoucherPagingData),
                flowOf(mockCategoryPagingData),
                flowOf(mockDrinkPagingData),
                User(1, "Linh", "", "", "", "", 3, 0, 0)
            ),
            event = {},
            onSearchClick = {}
        ) {}
    }
}
