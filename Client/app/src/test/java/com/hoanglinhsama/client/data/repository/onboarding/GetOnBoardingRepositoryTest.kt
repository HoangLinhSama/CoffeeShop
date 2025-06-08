package com.hoanglinhsama.client.data.repository.onboarding

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.mapper.toOnboardingDomain
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Onboarding
import com.hoanglinhsama.client.data.source.paging.OnboardingPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetOnBoardingRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: OnboardingPagingSource

    @Before
    fun setUp() {
        mainApi = Mockito.mock(MainApi::class.java)
        pagingSource = OnboardingPagingSource(mainApi)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 3
        val onboardingData = Onboarding(
            "",
            "Khám phá hương vị yêu thích",
            "Từ cà phê đậm đà, trà xanh thanh mát đến trà sữa béo ngậy,... chúng tôi có tất cả để bạn lựa chọn"
        )
        val response = MainResponse<Onboarding>(
            status = "success",
            result = listOf(onboardingData)
        )
        `when`(mainApi.getOnboarding(page, pageSize)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )

        val onboardingDomain = onboardingData.toOnboardingDomain()
        val expected = LoadResult.Page(
            data = listOf(onboardingDomain),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 3
        `when`(mainApi.getOnboarding(page, pageSize))
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
        val response = MainResponse<Onboarding>(
            status = "fail: no more data",
            result = emptyList()
        )
        `when`(mainApi.getOnboarding(1, 3)).thenReturn(Response.success(response))

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