package com.hoanglinhsama.client.data.repository.main

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.mapper.toShopDomain
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Shop
import com.hoanglinhsama.client.data.source.paging.ShopPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetShopRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: ShopPagingSource

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        pagingSource = ShopPagingSource(mainApi)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 5
        val shopData = Shop(
            1,
            "HCM Nguyễn Ảnh Thủ",
            "",
            "93/5 Nguyễn Ảnh Thủ, Huyện Hóc Môn, Hồ Chí Minh, Việt Nam",
            "0968674279",
            "07:00 - 21:30"
        )

        val response = MainResponse<Shop>(
            status = "success",
            result = listOf(shopData)
        )
        `when`(mainApi.getShop(page, pageSize)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )


        val shopDomain = shopData.toShopDomain()
        val expected = LoadResult.Page(
            data = listOf(shopDomain),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 5
        `when`(mainApi.getShop(page, pageSize))
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
        val response = MainResponse<Shop>(
            status = "fail: no more data",
            result = emptyList()
        )
        `when`(mainApi.getShop(1, 5)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = 5,
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