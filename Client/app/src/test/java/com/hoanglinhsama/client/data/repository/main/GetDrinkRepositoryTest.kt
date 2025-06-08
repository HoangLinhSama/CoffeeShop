package com.hoanglinhsama.client.data.repository.main

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.model.Drink
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.source.paging.DrinkPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetDrinkRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: DrinkPagingSource

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        pagingSource = DrinkPagingSource(mainApi)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 10
        val priceSizeData = listOf("Nhỏ:29000", "Vừa:39000", "Lớn:45000")
        val toppingPriceData =
            listOf("Shot Espresso:10000", "Trân châu trắng:10000")
        val drinkData = Drink(1, "Bạc Sỉu", "", 5F, "", priceSizeData, toppingPriceData, 1, "Cafe")
        val response = MainResponse<Drink>(
            status = "success",
            result = listOf(drinkData)
        )
        `when`(mainApi.getDrink(page, pageSize)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )


        val priceSizeDomain = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
        val toppingPriceDomain = mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000)
        val drinkDomain = com.hoanglinhsama.client.domain.model.Drink(
            1,
            "Bạc Sỉu",
            priceSizeDomain,
            "",
            5F,
            "",
            toppingPriceDomain,
            1,
            "Cafe"
        )
        val expected = LoadResult.Page(
            data = listOf(drinkDomain),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 10
        `when`(mainApi.getDrinkCategory(page, pageSize))
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
        val response = MainResponse<Drink>(
            status = "fail: no more data",
            result = emptyList()
        )
        `when`(mainApi.getDrink(1, 10)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = 10,
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