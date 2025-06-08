package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.hoanglinhsama.client.data.repository.AppRepositoryImplement
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.repository.AppRepository
import com.hoanglinhsama.client.domain.repository.AuthRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class AutoFillPhoneRepositoryTest {
    private val preferences: Preferences = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private lateinit var repository: AuthRepository
    private lateinit var mainApi: MainApi

    @Before
    fun setUp() {
        mainApi = mock(MainApi::class.java)
        repository = AuthRepositoryImplement(mainApi, userSettingDataStore)
    }

    @Test
    fun returnsStateSavePhone_whenStateSavePhoneIsSavedInDataStore() = runTest {
        whenever(preferences[PreferenceKey.CHECKED_SAVE_PHONE]).thenReturn(true)
        whenever(userSettingDataStore.data).thenReturn(flowOf(preferences))

        repository.autoFillPhone().test {
            val result = awaitItem()
            assertEquals(true, result)
            awaitComplete()
        }
    }
}