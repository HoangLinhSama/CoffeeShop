package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.remote.api.ApiUtil
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class UploadAvatarRepositoryTest {
    private lateinit var repository: AuthRepositoryImplement
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var mainApi: MainApi

    @Before
    fun setUp() {
        mainApi = mock()
        dataStore = mock()
        repository = AuthRepositoryImplement(mainApi, dataStore)
    }

    @Test
    fun shouldEmitSuccess_whenAPIRespondsWithSuccess() = runTest {
        val part = mock<MultipartBody.Part>()
        val expectedUrl = ApiUtil.BASE_URL + "picture/avatar/image.jpg"
        val responseBody = MainResponse(
            status = "success",
            result = listOf("image.jpg")
        )
        whenever(mainApi.upLoadAvatar(part)).thenReturn(Response.success(responseBody))

        repository.uploadAvatar(part).test {
            assertThat(awaitItem()).isEqualTo(Result.Loading)
            assertThat(awaitItem()).isEqualTo(Result.Success(expectedUrl))
            awaitComplete()
        }
    }

    @Test
    fun shouldEmitError_whenAPIRespondsWithFail() = runTest {
        val part = mock<MultipartBody.Part>()
        val responseBody = MainResponse<String>(
            status = "fail",
            result = emptyList()
        )
        whenever(mainApi.upLoadAvatar(part)).thenReturn(Response.success(responseBody))

        repository.uploadAvatar(part).test {
            assertThat(awaitItem()).isEqualTo(Result.Loading)
            val errorResult = awaitItem()
            assertThat(errorResult).isInstanceOf(Result.Error::class.java)
            awaitComplete()
        }
    }

    @Test
    fun shouldEmitError_whenAPIThrowsException() = runTest {
        val part = mock<MultipartBody.Part>()
        whenever(mainApi.upLoadAvatar(part)).thenThrow(RuntimeException("Network error"))

        repository.uploadAvatar(part).test {
            assertThat(awaitItem()).isEqualTo(Result.Loading)
            val errorResult = awaitItem()
            assertThat(errorResult).isInstanceOf(Result.Error::class.java)
            awaitComplete()
        }
    }
}