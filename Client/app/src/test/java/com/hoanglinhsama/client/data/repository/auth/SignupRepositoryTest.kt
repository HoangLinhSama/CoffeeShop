package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.MainApi
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

class SignupRepositoryTest {
    private lateinit var mainApi: MainApi
    private lateinit var repository: AuthRepositoryImplement
    private val userSettingDataStore: DataStore<Preferences> = mock()

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        repository = AuthRepositoryImplement(mainApi, userSettingDataStore)
    }

    @Test
    fun returnSuccess_whenAPIIsSuccessful_andStatusSuccess() = runTest {
        `when`(mainApi.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "", ""))
            .thenReturn(Response.success("success"))

        repository.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "").test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertEquals(Unit, (result as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun returnError_whenAPIIsSuccess_andStatusFail() = runTest {
        `when`(mainApi.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "", ""))
            .thenReturn(Response.success("fail"))

        repository.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "").test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertEquals(
                "fail",
                (result as Result.Error).exception.message
            )
            awaitComplete()
        }
    }

    @Test
    fun returnsError_WhenAPIFails_withHTTPError() = runTest {
        val errorResponse = Response.error<String>(
            500,
            "Internal Server Error".toResponseBody("text/plain".toMediaType())
        )

        `when`(mainApi.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "", "")).thenReturn(errorResponse)

        repository.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "").test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertTrue(
                (result as Result.Error).exception.message?.contains(
                    "API request failed"
                ) == true
            )
            awaitComplete()
        }
    }

    @Test
    fun returnsError_whenExceptionIsThrown() = runTest {
        `when`(mainApi.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "", "")).thenThrow(RuntimeException("Network error"))

        repository.signup("Linh", "Hoàng", "+84968674274", "Quận 12, Hồ Chí Minh", "").test {
            assertTrue(awaitItem() is Result.Loading)
            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertEquals("Network error", (result as Result.Error).exception.message)
            awaitComplete()
        }
    }
}