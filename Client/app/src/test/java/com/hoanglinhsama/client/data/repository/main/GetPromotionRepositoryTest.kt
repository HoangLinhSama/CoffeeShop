package com.hoanglinhsama.client.data.repository.main

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.mapper.toVoucherDomain
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Voucher
import com.hoanglinhsama.client.data.source.paging.VoucherPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetPromotionRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: VoucherPagingSource
    private lateinit var phone: String

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        phone = "+84968674274"
        pagingSource = VoucherPagingSource(mainApi, phone)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 3
        val voucherData = Voucher(
            3,
            "RONRANG20",
            "1/5",
            "30/6",
            "Giảm 20K Đơn 60K",
            " ",
            20000F,
            "delivery",
            false,
            60000,
            listOf(
                "Trà sữa",
                "Trà trái cây"
            ),
            "",
            ""
        )
        val response = MainResponse<Voucher>(
            status = "success",
            result = listOf(voucherData)
        )
        `when`(mainApi.getPromotion(page, pageSize, phone)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )
        val voucherDomain = voucherData.toVoucherDomain()
        val expected = LoadResult.Page(
            data = listOf(voucherDomain),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 3
        `when`(mainApi.getPromotion(page, pageSize, phone))
            .thenReturn(Response.error(500, "".toResponseBody(null)))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )
        assert(result is LoadResult.Error)
    }

    @Test
    fun shouldReturnEmpty_whenStatusIsFailNoMoreData() = runTest {
        val response = MainResponse<Voucher>(
            status = "fail: no more data",
            result = emptyList()
        )
        `when`(mainApi.getPromotion(1, 3, phone)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        val expected = LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )
        assertEquals(expected, result)
    }
}