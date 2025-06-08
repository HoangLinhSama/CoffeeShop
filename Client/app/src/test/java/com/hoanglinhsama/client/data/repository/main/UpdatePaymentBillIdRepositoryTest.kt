package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class UpdatePaymentBillIdRepositoryTest {
    private val mainApi: MainApi = mock()
    private lateinit var repository: MainRepositoryImplement
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val httpProvider: HttpProvider = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
    }

    @Test
    fun shouldEmitResultSuccess_andCallbackWithTrue_whenStatusIsSuccess_andResultIsTrue() =
        runTest {
            val orderId = 123
            val paymentBillId = "Bill123"
            val callbackResult = mutableListOf<Pair<String, Boolean?>>()

            val responseBody = MainResponse(
                status = "success",
                result = listOf(true)
            )
            whenever(
                mainApi.updatePaymentBillId(
                    orderId,
                    paymentBillId
                )
            ).thenReturn(Response.success(responseBody))

            val flow = repository.updatePaymentBillId(orderId, paymentBillId) { status, value ->
                callbackResult.add(status to value)
            }

            flow.test {
                val loading = awaitItem()
                assertThat(loading).isInstanceOf(Result.Loading::class.java)
                val success = awaitItem()
                assertThat(success).isInstanceOf(Result.Success::class.java)
                cancelAndIgnoreRemainingEvents()
            }
            assertThat(callbackResult).containsExactly("success" to true)
        }

    @Test
    fun shouldCallbackWithNull_whenStatusIsFail() = runTest {
        val orderId = 123
        val paymentBillId = "ABC123"
        val callbackResult = mutableListOf<Pair<String, Boolean?>>()

        val responseBody: MainResponse<Boolean> = MainResponse(
            status = "fail",
            result = emptyList()
        )
        whenever(mainApi.updatePaymentBillId(orderId, paymentBillId)).thenReturn(
            Response.success(
                responseBody
            )
        )

        repository.updatePaymentBillId(orderId, paymentBillId) { status, value ->
            callbackResult.add(status to value)
        }.collect {

        }

        assertThat(callbackResult).containsExactly("fail" to null)
    }

    @Test
    fun shouldCallbackWithExceptionMessage_whenAPIThrowsException() = runTest {
        val orderId = 123
        val paymentBillId = "ABC123"
        val callbackResult = mutableListOf<Pair<String, Boolean?>>()

        val errorMessage = "Network error"
        whenever(mainApi.updatePaymentBillId(orderId, paymentBillId)).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        repository.updatePaymentBillId(orderId, paymentBillId) { status, value ->
            callbackResult.add(status to value)
        }.collect {

        }

        assertThat(callbackResult.first().first).contains(errorMessage)
        assertThat(callbackResult.first().second).isNull()
    }
}