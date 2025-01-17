package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.domain.model.Page
import com.hoanglinhsama.client.presentation.viewmodel.state.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.hoanglinhsama.client.R

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(OnBoardingState())
    val state = _state

    init {
        initDataPage()
    }

    private fun initDataPage() {
        val lisPage = listOf(
            Page(
                R.drawable.img_onboarding_1,
                "Khám phá hương vị yêu thích",
                "Từ cà phê đậm đà, trà xanh thanh mát đến trà sữa béo ngậy,... chúng tôi có tất cả để bạn lựa chọn"
            ),
            Page(
                R.drawable.img_onboarding_2,
                "Thanh toán tiện lợi, bảo mật cao",
                "Hỗ trợ nhiều phương thức thanh toán điện tử giúp bạn dễ dàng hoàn tất đơn hàng chỉ trong vài giây"
            ),
            Page(
                R.drawable.img_onboarding_3,
                "Đặt hàng dễ dàng, nhanh chóng",
                "Chỉ với vài thao tác, đồ uống thơm ngon sẽ được giao tận tay bạn ngay lập tức"
            )
        )
        _state.value = _state.value.copy(_listPage = lisPage)
    }
}