package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import com.hoanglinhsama.client.domain.repository.MainRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPhoneRepositoryTest {
    private val preferences: Preferences = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private val mainApi: MainApi = mock()
    private val httpProvider: HttpProvider = mock()
    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        repository = MainRepositoryImplement(mainApi, userSettingDataStore, httpProvider)
    }

    @Test
    fun returnsPhone_whenPhoneIsSavedInDataStore() = runTest {
        whenever(preferences[PreferenceKey.PHONE]).thenReturn("+84968674274")
        whenever(userSettingDataStore.data).thenReturn(flowOf(preferences))

        repository.getPhone().test {
            val result = awaitItem()
            assertEquals("+84968674274", result)
            awaitComplete()
        }
    }

    @Test
    fun returnsEmpty_whenPhoneIsNotSaved() = runTest {
        whenever(preferences[PreferenceKey.PHONE]).thenReturn(null)
        whenever(userSettingDataStore.data).thenReturn(flowOf(preferences))

        repository.getPhone().test {
            val result = awaitItem()
            assertEquals("", result)
            awaitComplete()
        }
    }
}