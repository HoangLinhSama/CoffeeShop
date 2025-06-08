package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class InsertStatusOrderRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var repository: MainRepositoryImplement
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val httpProvider: HttpProvider = mock()

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
    }

    @Test
    fun returnSuccess_whenAPIIsSuccessful_andStatusSuccess() = runTest {
        `when`(mainApi.insertStatusOrder(101, 3))
            .thenReturn(Response.success("success"))

        repository.insertStatusOrder(101, 3).test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(Unit, (result as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun returnError_whenAPIIsSuccess_andStatusFail() = runTest {
        `when`(mainApi.insertStatusOrder(101, 3))
            .thenReturn(Response.success("fail"))

        repository.insertStatusOrder(101, 3).test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertEquals("fail", (result as Result.Error).exception.message)
            awaitComplete()
        }
    }

    @Test
    fun returnsError_WhenAPIFails_withHTTPError() = runTest {
        val errorResponse = Response.error<String>(
            500,
            "Internal Server Error".toResponseBody("text/plain".toMediaType())
        )

        `when`(mainApi.insertStatusOrder(101, 3)).thenReturn(errorResponse)

        repository.insertStatusOrder(101, 3).test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception.message?.contains("API request failed") == true)
            awaitComplete()
        }
    }

    @Test
    fun returnsError_whenExceptionIsThrown() = runTest {
        `when`(mainApi.insertStatusOrder(101, 3)).thenThrow(RuntimeException("Network error"))

        repository.insertStatusOrder(101, 3).test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertEquals("Network error", (result as Result.Error).exception.message)
            awaitComplete()
        }
    }
}