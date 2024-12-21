package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoanglinhsama.client.R
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
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkSlateGray
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.widget.DrinkCard
import com.hoanglinhsama.client.presentation.view.widget.DrinkCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.PromotionCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCardShimmerEffect
import com.hoanglinhsama.client.presentation.view.widget.SearchBar
import com.hoanglinhsama.client.util.handlePagingResult
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    user: User? = null,
    itemsVoucher: LazyPagingItems<Voucher>? = null,
    itemsDrink: LazyPagingItems<Drink>? = null,
    itemsDrinkCategory: LazyPagingItems<DrinkCategory>? = null,
    onNotificationClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onFilterClick: () -> Unit,
    onAvatarClick: () -> Unit,
    onVoucherClick: () -> Unit,
    onSearchDrink: (String) -> Unit,
    onDrinkCategoryClick: (String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (constraintLayout1, constraintLayout2) = createRefs()
        val (rowBar, rowSearch, spacer, promotionCard) = createRefs()
        ConstraintLayout(modifier = Modifier
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
                val context = LocalContext.current
                Surface(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = onAvatarClick
                        )
                ) {
                    AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = ImageRequest.Builder(context).data(user?.image).build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "Xin chào, " + user?.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onNotificationClick
                ) {
                    Icon(
                        Icons.Outlined.Notifications, contentDescription = null, tint = Color.White
                    )
                }
                IconButton(
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder, contentDescription = null, tint = Color.White
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
                    .wrapContentHeight(), "", "Tìm đồ uống", onFilterClick, onSearchDrink
            ) {

            }
            Box(modifier = Modifier
                .constrainAs(promotionCard) {
                    top.linkTo(rowSearch.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }) {
                if (handlePagingResult(
                        items = itemsVoucher,
                        Modifier
                            .height(140.dp)
                            .fillMaxWidth()
                    ) {
                        PromotionCardShimmerEffect(
                            Modifier.height(140.dp)
                        )
                    }
                ) {
                    var currentIndex by remember { mutableIntStateOf(0) }
                    val autoFlipInterval = 3000L
                    LaunchedEffect(key1 = currentIndex) {
                        delay(autoFlipInterval)
                        currentIndex = (currentIndex + 1) % itemsVoucher!!.itemCount
                    }
                    for (index in 0 until (itemsVoucher!!.itemCount)) {
                        AnimatedVisibility(
                            visible = index == currentIndex,
                            enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
                            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }),
                        ) {
                            PromotionCard(
                                modifier = Modifier.height(140.dp),
                                voucher = itemsVoucher[index]!!,
                                pageSize = itemsVoucher.itemCount,
                                selectedPage = currentIndex,
                                onClick = onVoucherClick
                            )
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
        ConstraintLayout(modifier = Modifier
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
                var selectedItem by remember { mutableIntStateOf(-1) }
                if (handlePagingResult(itemsDrinkCategory) {
                        Row(
                            modifier = Modifier.padding(top = Dimens.mediumMargin),
                            verticalAlignment = Alignment.CenterVertically
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
                                if (it < 3) {
                                    Spacer(modifier = Modifier.size(8.dp))
                                }
                            }
                        }
                    }) {
                    LazyRow(
                        modifier = Modifier.padding(top = Dimens.mediumMargin),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(count = itemsDrinkCategory!!.itemCount) {
                            val isSelected = selectedItem == it
                            Button(
                                onClick = {
                                    onDrinkCategoryClick(itemsDrinkCategory[it]!!.name)
                                    selectedItem = it
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
            Column(modifier = Modifier
                .constrainAs(drinkLazyVerticalGrid) {
                    top.linkTo(drinkCategoryLazyRow.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }) {
                if (handlePagingResult(itemsDrink, Modifier.aspectRatio(1f)) {
                        Column {
                            repeat(2) {
                                Row {
                                    repeat(2) {
                                        DrinkCardShimmerEffect(
                                            modifier = Modifier
                                                .height(250.dp)
                                                .width(160.dp)
                                        )
                                        if (it < 2) {
                                            Spacer(Modifier.size(Dimens.mediumMargin))
                                        }
                                    }
                                }
                                if (it < 2) {
                                    Spacer(Modifier.size(Dimens.mediumMargin))
                                }
                            }
                        }
                    }) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            bottom = 530.dp
                        )
                    ) {
                        items(itemsDrink!!.itemCount) {
                            DrinkCard(
                                drink = itemsDrink[it]!!
                            )
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
    ClientTheme(dynamicColor = false) {
        HomeScreen(null, null, null, null, {}, {}, {}, {}, {}, {}) {

        }
    }
}