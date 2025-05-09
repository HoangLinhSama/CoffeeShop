package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.hoanglinhsama.client.domain.model.Onboarding
import com.hoanglinhsama.client.presentation.view.ui.theme.ChineseBlack
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal1
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.widget.OnBoardingPage
import com.hoanglinhsama.client.presentation.view.widget.PagerIndicator
import com.hoanglinhsama.client.presentation.viewmodel.event.OnBoardingEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OnBoardingState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    event: (OnBoardingEvent) -> Unit,
    onButtonNextClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(ChineseBlack, DarkCharcoal1),
                    start = Offset.Zero,
                    end = Offset(0F, 1000F),
                )
            )
    ) {
        val itemsOnboarding = state.itemsOnboarding?.collectAsLazyPagingItems()
        val pagerState = rememberPagerState(initialPage = 0) {
            itemsOnboarding?.itemCount!!
        }
        val scope = rememberCoroutineScope()
        HorizontalPager(state = pagerState) {
            OnBoardingPage(itemsOnboarding?.get(it)!!)
        }

        Column(
            modifier = Modifier
                .padding(
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin,
                    bottom = Dimens.mediumMargin
                )
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PagerIndicator(
                pageSize = itemsOnboarding?.itemCount!!,
                selectedPage = pagerState.currentPage,
            )
            Spacer(modifier = Modifier.size(Dimens.mediumMargin))
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < itemsOnboarding.itemCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            event(OnBoardingEvent.UpdateStateEnterAppEvent)
                            onButtonNextClick()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.buttonHeight),
                shape = RoundedCornerShape(Dimens.roundedCornerSize)
            ) {
                val text = when (pagerState.currentPage) {
                    0 -> "Bắt đầu"
                    1 -> "Tiếp"
                    2 -> "Khám phá ngay"
                    else -> "Tiếp"
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    ClientTheme(dynamicColor = false) {
        val listOnboarding = listOf(
            Onboarding(
                "",
                "Khám phá hương vị yêu thích",
                "Từ cà phê đậm đà, trà xanh thanh mát đến trà sữa béo ngậy,... chúng tôi có tất cả để bạn lựa chọn"
            ),
            Onboarding(
                "",
                "Thanh toán tiện lợi, bảo mật cao",
                "Hỗ trợ nhiều phương thức thanh toán điện tử giúp bạn dễ dàng hoàn tất đơn hàng chỉ trong vài giây"
            ),
            Onboarding(
                "",
                "Đặt hàng dễ dàng, nhanh chóng",
                "Chỉ với vài thao tác, đồ uống thơm ngon sẽ được giao tận tay bạn ngay lập tức"
            )
        )
        val mockOnboardingPagingData = PagingData.from(listOnboarding)
        OnBoardingScreen(OnBoardingState(flowOf(mockOnboardingPagingData)), {}) {

        }
    }
}