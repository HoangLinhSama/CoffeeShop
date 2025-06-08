package com.hoanglinhsama.client.data.repository.main

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.model.DrinkCategory
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.source.paging.DrinkCategoryPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetDrinkCategoryRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: DrinkCategoryPagingSource

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        pagingSource = DrinkCategoryPagingSource(mainApi)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 3
        val response = MainResponse(
            status = "success",
            result = listOf(DrinkCategory("Cafe"))
        )
        `when` (mainApi.getDrinkCategory(page, pageSize)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )

        val expected = LoadResult.Page(
            data = listOf(com.hoanglinhsama.client.domain.model.DrinkCategory("Cafe")),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 3
        `when` (mainApi.getDrinkCategory(page, pageSize))
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
        val response = MainResponse<DrinkCategory>(
            status = "fail: no more data",
            result = emptyList()
        )
        `when` (mainApi.getDrinkCategory(1, 3)).thenReturn(Response.success(response))

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