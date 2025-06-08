package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import com.hoanglinhsama.client.data.source.remote.zalopay.config.AppInfo
import com.hoanglinhsama.client.domain.model.OrderZaloPay
import com.hoanglinhsama.client.presentation.viewmodel.OrderZaloPayItems
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CreateOrderZaloPayRepositoryTest {
    private val httpProvider: HttpProvider = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val mainApi: MainApi = mock()
    private lateinit var repository: MainRepositoryImplement

    @Before
    fun setup() {
        repository = MainRepositoryImplement(
            mainApi, userSettingDataStore, httpProvider
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun returnsJSONObject_whenCreateOrderZaloPayIsSuccessful() = runTest {
        val expectedJson = JSONObject().apply {
            put("return_code", 1)
            put("return_message", "Success")
        }

        val items = listOf<OrderZaloPayItems>(
            OrderZaloPayItems(300, 59000F, 1)
        )
        val orderZaloPay = OrderZaloPay(
            _appId = 2553,
            _appUser = "test_user",
            _appTransId = "240606_000001",
            _appTime = 1620000000000,
            _amount = 59000L,
            _item = Gson().toJson(items),
            _description = "CoffeeShop thanh toán đơn hàng #300",
            _embedData = "{}",
            _bankCode = "zalopayapp",
            _mac = "test_mac"
        )

        whenever(httpProvider.sendPost(eq(AppInfo.URL_CREATE_ORDER), any()))
            .thenReturn(expectedJson)

        val result = repository.createOrderZaloPay(orderZaloPay)

        assertNotNull(result)
        assertEquals(1, result?.getInt("return_code"))
        assertEquals("Success", result?.getString("return_message"))
    }

    @Test
    fun returnsNull_whenHttpProviderFails() = runTest {
        val items = listOf<OrderZaloPayItems>(
            OrderZaloPayItems(300, 59000F, 1)
        )
        val orderZaloPay = OrderZaloPay(
            _appId = 2553,
            _appUser = "test_user",
            _appTransId = "240606_000001",
            _appTime = 1620000000000,
            _amount = 59000L,
            _item = Gson().toJson(items),
            _description = "CoffeeShop thanh toán đơn hàng #300",
            _embedData = "{}",
            _bankCode = "zalopayapp",
            _mac = "test_mac"
        )

        whenever(httpProvider.sendPost(any(), any())).thenReturn(null)

        val result = repository.createOrderZaloPay(orderZaloPay)

        assertNull(result)
    }
}