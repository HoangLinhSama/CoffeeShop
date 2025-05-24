package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.MilkChocolate
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import com.hoanglinhsama.client.presentation.view.widget.PromotionCard
import com.hoanglinhsama.client.presentation.view.widget.PromotionCardShimmerEffect
import com.hoanglinhsama.client.presentation.viewmodel.state.PromotionState
import kotlinx.coroutines.flow.flowOf

@Composable
fun PromotionScreen(
    state: PromotionState,
    onUseVoucherClick: () -> Unit,
    onFeatureItemClick: (Int) -> Unit,
) {
    val itemsVoucher = state.itemsVoucher?.collectAsLazyPagingItems()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (column1, column2) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(column1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(CopperRed, MilkChocolate),
                        start = Offset.Zero,
                        end = Offset(0F, 500F),
                    )
                )
                .padding(bottom = Dimens.mediumMargin)) {
            Text(
                text = "Ưu đãi",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.sizeTitle
                ),
                color = Color.White,
                modifier = Modifier.padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin,
                )
            ) {
                Text(
                    text = state.currentBean.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = Dimens.sizeTitle
                    ),
                    color = Color.White,
                )
                Icon(
                    painterResource(R.drawable.ic_coffee_bean),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    shape = RoundedCornerShape(Dimens.roundedCornerSize), onClick = {
                        onUseVoucherClick()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Icon(
                        painterResource(R.drawable.ic_voucher),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = Dimens.smallMargin),
                        tint = CopperRed
                    )
                    Text(
                        text = "Voucher của tôi",
                        style = MaterialTheme.typography.labelMedium,
                        color = CopperRed
                    )
                }
            }
            state.requireBean?.let {
                Text(
                    text = "Còn $it BEAN nữa bạn sẽ thăng hạng. \nĐổi quà không ảnh hưởng tới việc thăng hạng của bạn.\nHãy dùng BEAN này để đổi ưu đãi nhé. ",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color.White,
                    modifier = Modifier.padding(
                        top = Dimens.mediumMargin,
                        start = Dimens.mediumMargin,
                        end = Dimens.mediumMargin
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .constrainAs(column2) {
                    top.linkTo(column1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxSize()
                .background(Cultured)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimens.smallMargin / 2),
                horizontalArrangement = Arrangement.spacedBy(Dimens.smallMargin / 2),
                modifier = Modifier.padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                ),
            ) {
                state.listFeatureItem?.let { featureItem ->
                    items(featureItem.size) { index ->
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(Dimens.roundedCornerSize / 2))
                                .background(Color.White)
                                .clickable {
                                    onFeatureItemClick(index)
                                }
                                .wrapContentHeight()) {
                            featureItem[index].color?.let {
                                Icon(
                                    painterResource(featureItem[index].icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(
                                            top = Dimens.smallMargin,
                                            start = Dimens.smallMargin,
                                            end = Dimens.smallMargin
                                        )
                                        .size(24.dp),
                                    tint = it
                                )
                            }
                            Text(
                                text = featureItem[index].title,
                                modifier = Modifier.padding(
                                    top = Dimens.smallMargin,
                                    start = Dimens.smallMargin,
                                    end = Dimens.smallMargin,
                                    bottom = Dimens.smallMargin
                                ),
                                color = DarkCharcoal2,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        top = Dimens.mediumMargin,
                        start = Dimens.mediumMargin,
                        end = Dimens.mediumMargin
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Voucher của bạn",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = Dimens.sizeSubTitle
                    ),
                    modifier = Modifier
                        .padding(end = Dimens.mediumMargin)
                        .weight(1f)
                )
                Text(
                    text = "Xem tất cả",
                    style = MaterialTheme.typography.labelMedium,
                    color = CopperRed,
                    modifier = Modifier.clickable {
                        onUseVoucherClick()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .padding(
                        top = Dimens.mediumMargin,
                        start = Dimens.mediumMargin,
                        end = Dimens.mediumMargin
                    )
                    .fillMaxWidth()
            ) {
                itemsVoucher?.let { itemsVoucher ->
                    if (handlePagingResult(
                            itemsVoucher, Modifier
                                .fillMaxWidth()
                                .height(140.dp), DarkCharcoal2
                        ) {
                            Column {
                                repeat(2) {
                                    PromotionCardShimmerEffect(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(140.dp)
                                    )
                                    if (it < 2) {
                                        Spacer(Modifier.size(Dimens.mediumMargin))
                                    }
                                }
                            }
                        }
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin),
                            contentPadding = PaddingValues(
                                bottom = 350.dp
                            )
                        ) {
                            items(count = 3) {
                                itemsVoucher[it]?.let { voucher ->
                                    PromotionCard(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(140.dp), voucher, {

                                        }, null
                                    )
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
fun PromotionScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val listFeatureItem = listOf(
            FeatureItem(R.drawable.ic_membership, "Hạng thành viên", Color.Yellow, null),
            FeatureItem(R.drawable.ic_gift_box, "Đổi bean", Color.Red, null),
            FeatureItem(R.drawable.ic_coffee_bean, "Lịch sử bean", CopperRed, null),
            FeatureItem(R.drawable.ic_benefit, "Quyền lơi của bạn", Color.Blue, null)
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
        PromotionScreen(
            PromotionState(
                _listFeatureItem = listFeatureItem,
                _currentBean = 5,
                _requiredBean = 195,
                _itemsVoucher = flowOf(mockVoucherPagingData)
            ), {}) {}
    }
}