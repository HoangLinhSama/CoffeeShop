package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class CheckHadAccountRepositoryTest {
    private val mainApi: MainApi = mock()
    private lateinit var repository:AuthRepositoryImplement
    private val userSettingDataStore: DataStore<Preferences> = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        repository = AuthRepositoryImplement(mainApi, userSettingDataStore)
    }

    @Test
    fun shouldEmitResultSuccess_andCallbackWithTrue_whenStatusIsSuccess() =
        runTest {
            val phone = "+8498684274"
            val callbackResult = mutableListOf<Pair<String, Boolean?>>()

            val responseBody = MainResponse(
                status = "success",
                result = listOf(true)
            )
            whenever(
                mainApi.checkHadAccount(phone)
            ).thenReturn(Response.success(responseBody))

            val flow = repository.checkHadAccount(phone) { status, value ->
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
        val phone ="+84968674274"
        val callbackResult = mutableListOf<Pair<String, Boolean?>>()

        val responseBody: MainResponse<Boolean> = MainResponse(
            status = "fail",
            result = emptyList()
        )
        whenever(mainApi.checkHadAccount(phone)).thenReturn(
            Response.success(
                responseBody
            )
        )

        repository.checkHadAccount(phone) { status, value ->
            callbackResult.add(status to value)
        }.collect {

        }

        assertThat(callbackResult).containsExactly("fail" to null)
    }

    @Test
    fun shouldCallbackWithExceptionMessage_whenAPIThrowsException() = runTest {
       val phone ="+84968674274"
        val callbackResult = mutableListOf<Pair<String, Boolean?>>()

        val errorMessage = "Network error"
        whenever(mainApi.checkHadAccount(phone)).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        repository.checkHadAccount(phone) { status, value ->
            callbackResult.add(status to value)
        }.collect {

        }

        assertThat(callbackResult.first().first).contains(errorMessage)
        assertThat(callbackResult.first().second).isNull()
    }
}