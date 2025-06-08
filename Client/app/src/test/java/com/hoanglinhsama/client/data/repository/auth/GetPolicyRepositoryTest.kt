package com.hoanglinhsama.client.data.repository.auth

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Policies
import com.hoanglinhsama.client.data.source.paging.PolicyPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetPolicyRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var pagingSource: PolicyPagingSource

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        pagingSource = PolicyPagingSource(mainApi)
    }

    @Test
    fun shouldReturnPage_whenApiSuccess() = runTest {
        val page = 1
        val pageSize = 4
        val policyData = Policies(
            "1. Chính sách bảo mật",
            "Ứng dụng thu thập thông tin như họ tên, số điện thoại, email, địa chỉ khi khách hàng đăng ký tài khoản hoặc đặt hàng.\n" + "Các thông tin này chỉ được sử dụng để phục vụ nhu cầu mua hàng, giao hàng, chăm sóc khách hàng và cải thiện dịch vụ.\n" + "Dữ liệu cá nhân của khách hàng được mã hóa và lưu trữ an toàn.\n" + "Ứng dụng không chia sẻ thông tin người dùng với bên thứ ba mà không có sự đồng ý của họ."
        )
        val response = MainResponse<Policies>(
            status = "success", result = listOf(policyData)
        )
        `when`(mainApi.getPolicy(page, pageSize)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null, loadSize = pageSize, placeholdersEnabled = false
            )
        )

        val policyDomain = com.hoanglinhsama.client.domain.model.Policies(
            "1. Chính sách bảo mật",
            "Ứng dụng thu thập thông tin như họ tên, số điện thoại, email, địa chỉ khi khách hàng đăng ký tài khoản hoặc đặt hàng.\n" + "Các thông tin này chỉ được sử dụng để phục vụ nhu cầu mua hàng, giao hàng, chăm sóc khách hàng và cải thiện dịch vụ.\n" + "Dữ liệu cá nhân của khách hàng được mã hóa và lưu trữ an toàn.\n" + "Ứng dụng không chia sẻ thông tin người dùng với bên thứ ba mà không có sự đồng ý của họ."
        )
        val expected = LoadResult.Page(
            data = listOf(policyDomain), prevKey = null, nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnError_whenApiFails() = runTest {
        val page = 1
        val pageSize = 4
        `when`(mainApi.getPolicy(page, pageSize)).thenReturn(
            Response.error(
                500,
                "".toResponseBody(null)
            )
        )

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null, loadSize = pageSize, placeholdersEnabled = false
            )
        )
        assert(result is LoadResult.Error)
    }

    @Test
    fun shouldReturnEmpty_whenStatusIsFailNoMoreData() = runTest {
        val response = MainResponse<Policies>(
            status = "fail: no more data", result = emptyList()
        )
        `when`(mainApi.getPolicy(1, 4)).thenReturn(Response.success(response))

        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null, loadSize = 4, placeholdersEnabled = false
            )
        )

        val expected = LoadResult.Page(
            data = emptyList(), prevKey = null, nextKey = null
        )
        assertEquals(expected, result)
    }
}