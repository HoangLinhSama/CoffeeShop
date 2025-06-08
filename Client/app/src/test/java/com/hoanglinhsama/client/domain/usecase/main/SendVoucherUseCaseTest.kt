package com.hoanglinhsama.client.domain.usecase.main

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.viewmodel.common.VoucherHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SendVoucherUseCaseTest {
    private lateinit var useCase: SendVoucherUseCase
    private val voucher1 = Voucher(
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
    private val voucher2 = Voucher(
        3,
        "RONRANG20",
        "1/5",
        "30/6",
        "Giảm 20K Đơn 60K",
        "Giảm 20K cho đơn từ 60K\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho trà trái cây, trà sữa.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.",
        20000F,
        "takeaway",
        false,
        60000,
        listOf(
            "Trà sữa",
            "Trà trái cây"
        ), "", ""
    )

    @Before
    fun setup() {
        useCase = SendVoucherUseCase()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun shouldUpdateVoucherHolderWithCorrectPagingData() = runTest {
        val pagingData = PagingData.from(listOf(voucher1, voucher2))

        useCase(pagingData)

        val job = launch {
            val result = VoucherHolder.voucher
                .filterNotNull()
                .asSnapshot()

            assertThat(result).hasSize(2)
            assertThat(result).containsExactly(voucher1, voucher2)
        }
        advanceUntilIdle()
        job.cancel()
    }
}