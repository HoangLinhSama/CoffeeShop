package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.hoanglinhsama.client.data.model.DrinkOrder
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyFloat
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import retrofit2.Response


class InsertOrderRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var repository: MainRepositoryImplement
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val httpProvider: HttpProvider = mock()
    lateinit var drinkOrderData: DrinkOrder

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
        drinkOrderData = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
    }

    @Test
    fun returnsSuccess_whenAPIIsSuccessful_andStatusIsSuccess() = runTest {
        val response = MainResponse("success", listOf(123))

        `when`(
            mainApi.insertOrder(
                userId = 63,
                name = "Linh Hoàng",
                phone = "+84968674274",
                address = "Quận 12, Hồ Chí Minh",
                dateTime = "2025-05-23 08:17:31",
                quantityBeanUse = null,
                paymentMethod = "Tiền mặt",
                shopId = null,
                isDelivery = true,
                deliveryFee = 15000f,
                subTotal = 168000f,
                totalPrice = 183000f,
                voucherId = null,
                paymentBillId = null,
                listDrinkOrder = Gson().toJson(listOf(drinkOrderData))
            )
        ).thenReturn(Response.success(response))

        repository.insertOrder(
            userId = 63,
            name = "Linh Hoàng",
            phone = "+84968674274",
            address = "Quận 12, Hồ Chí Minh",
            dateTime = "2025-05-23 08:17:31",
            quantityBeanUse = null,
            paymentMethod = "Tiền mặt",
            shopId = null,
            isDelivery = true,
            deliveryFee = 15000f,
            subTotal = 168000f,
            totalPrice = 183000f,
            voucherId = null,
            paymentBillId = null,
            listDrinkOrder = listOf(drinkOrderData)
        ).test {
            assertThat(awaitItem()).isInstanceOf(Result.Loading::class.java)
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(123)
            awaitComplete()
        }
    }

    @Test
    fun returnsError_whenAPIIsSuccessful_butStatusIsFail() = runTest {
        val response: MainResponse<Int> = MainResponse("fail: unknown error", null)

        `when`(
            mainApi.insertOrder(
                anyInt(), anyString(), anyString(), anyString(), anyString(),
                anyOrNull(), anyString(), anyOrNull(), anyBoolean(),
                anyFloat(), anyFloat(), anyFloat(), anyOrNull(), anyString(), anyString()
            )
        ).thenReturn(Response.success(response))

        repository.insertOrder(
            userId = 63,
            name = "Linh Hoàng",
            phone = "+84968674274",
            address = "Quận 12, Hồ Chí Minh",
            dateTime = "2025-05-23 08:17:31",
            quantityBeanUse = null,
            paymentMethod = "Tiền mặt",
            shopId = null,
            isDelivery = true,
            deliveryFee = 15000f,
            subTotal = 168000f,
            totalPrice = 183000f,
            voucherId = null,
            paymentBillId = null,
            listDrinkOrder = listOf(drinkOrderData)
        ).test {
            assertThat(awaitItem()).isInstanceOf(Result.Loading::class.java)
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Error::class.java)
            awaitComplete()
        }
    }

    @Test
    fun returnsError_whenAPIIsUnsuccessful() = runTest {
        val errorResponse = Response.error<MainResponse<Int>>(
            500,
            "Server error".toResponseBody("application/json".toMediaTypeOrNull())
        )

        `when`(
            mainApi.insertOrder(
                anyInt(), anyString(), anyString(), anyString(), anyString(),
                anyOrNull(), anyString(), anyOrNull(), anyBoolean(),
                anyFloat(), anyFloat(), anyFloat(), anyOrNull(), anyString(), anyString()
            )
        ).thenReturn(errorResponse)

        repository.insertOrder(
            userId = 63,
            name = "Linh Hoàng",
            phone = "+84968674274",
            address = "Quận 12, Hồ Chí Minh",
            dateTime = "2025-05-23 08:17:31",
            quantityBeanUse = null,
            paymentMethod = "Tiền mặt",
            shopId = null,
            isDelivery = true,
            deliveryFee = 15000f,
            subTotal = 168000f,
            totalPrice = 183000f,
            voucherId = null,
            paymentBillId = null,
            listDrinkOrder = listOf(drinkOrderData)
        ).test {
            assertThat(awaitItem()).isInstanceOf(Result.Loading::class.java)
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Error::class.java)
            awaitComplete()
        }
    }
}