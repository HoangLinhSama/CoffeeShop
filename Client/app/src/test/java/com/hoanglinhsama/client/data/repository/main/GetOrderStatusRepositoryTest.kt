package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hoanglinhsama.client.data.mapper.toOrderStatusDomain
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.OrderStatus
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import com.hoanglinhsama.client.domain.repository.MainRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetOrderStatusRepositoryTest {
    private lateinit var repository: MainRepository
    private val mainApi: MainApi = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val httpProvider: HttpProvider = mock()
    val orderId = 303

    @Before
    fun setUp() {
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
    }

    @Test
    fun returnsSuccess_whenAPI_isSuccessful_andStatus_isSuccess() = runTest {
        val orderStatusData = OrderStatus(
            isDelivery = true,
            listStatus = emptyList(),
            name = "Linh Hoàng",
            phone = "+84968674274",
            userId = "63",
            address = "Quận 12, Hồ Chí Minh",
            listDrinkOrder = emptyList(),
            shopName = null,
            shopAddress = null,
            subTotal = 128000f,
            deliveryFee = 15000f,
            discount = 20000f,
            totalPrice = 123000f,
            methodPayment = "ZaloPay",
            quantityBeanUse = null,
            voucherName = "Giảm 20K Đơn 60K"
        )

        val response = MainResponse<OrderStatus>(
            status = "success",
            result = listOf(orderStatusData)
        )
        `when`(mainApi.getOrderStatus(orderId)).thenReturn(Response.success(response))

        val result = repository.getOrderStatus(orderId).toList()
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Success)
        val data = result[1] as Result.Success
        val orderStatusDomain = orderStatusData.toOrderStatusDomain()
        assertEquals(orderStatusDomain, data.data)
    }


    @Test
    fun returnsError_whenAPIIsSuccessful_butStatusIsFail() = runTest {
        val apiResponse = MainResponse<OrderStatus>(
            status = "fail: unknown error",
            result = emptyList()
        )

        `when`(mainApi.getOrderStatus(orderId)).thenReturn(Response.success(apiResponse))

        val result = repository.getOrderStatus(orderId).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("fail: unknown error", error.exception.message)
    }

    @Test
    fun returnsError_whenAPIFails_withNon200StatusCode() = runTest {
        val response: Response<MainResponse<OrderStatus>> = Response.error(
            500,
            "Internal Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )

        `when`(mainApi.getOrderStatus(orderId)).thenReturn(response)

        val result = repository.getOrderStatus(orderId).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("API request failed with status: 500", error.exception.message)
    }

    @Test
    fun returnsError_whenExceptionThrown() = runTest {
        val exception = RuntimeException("Network failure")
        `when`(mainApi.getOrderStatus(orderId)).thenThrow(exception)
        val result = repository.getOrderStatus(orderId).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("Network failure", error.exception.message)
    }
}