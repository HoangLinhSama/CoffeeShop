package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hoanglinhsama.client.data.model.MainResponse
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

class GetRequiredBeanRepositoryTest {
    private lateinit var repository: MainRepository
    private val mainApi: MainApi = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val httpProvider: HttpProvider = mock()
    private lateinit var phone: String

    @Before
    fun setUp() {
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
        phone = "+84968674274"
    }

    @Test
    fun returnsSuccess_whenAPI_isSuccessful_andStatus_isSuccess() = runTest {
        val requiredBean = 5
        val response = MainResponse<Int>(
            status = "success",
            result = listOf(requiredBean)
        )
        `when`(mainApi.getRequiredBean(phone)).thenReturn(Response.success(response))

        val result = repository.getRequiredBean(phone).toList()
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Success)
        val data = result[1] as Result.Success
        assertEquals(requiredBean, data.data)
    }

    @Test
    fun returnsError_whenAPIIsSuccessful_butStatusIsFail() = runTest {
        val apiResponse = MainResponse<Int>(
            status = "fail: no data found",
            result = emptyList()
        )

        `when`(mainApi.getRequiredBean(phone)).thenReturn(Response.success(apiResponse))

        val result = repository.getRequiredBean(phone).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("fail: no data found", error.exception.message)
    }

    @Test
    fun returnsError_whenAPIFails_withNon200StatusCode() = runTest {
        val response: Response<MainResponse<Int>> = Response.error(
            500,
            "Internal Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )

        `when`(mainApi.getRequiredBean(phone)).thenReturn(response)

        val result = repository.getRequiredBean(phone).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("API request failed with status: 500", error.exception.message)
    }

    @Test
    fun returnsError_whenExceptionThrown() = runTest {
        val exception = RuntimeException("Network failure")
        `when`(mainApi.getRequiredBean(phone)).thenThrow(exception)
        val result = repository.getRequiredBean(phone).toList()

        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
        val error = result[1] as Result.Error
        assertEquals("Network failure", error.exception.message)
    }
}