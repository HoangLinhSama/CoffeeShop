package com.hoanglinhsama.client.data.repository.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.hoanglinhsama.client.data.repository.AppRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.domain.repository.AppRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckFirstTimeEnterAppRepositoryTest {
    private val preferences: Preferences = mock()
    private val userSettingDataStore: DataStore<Preferences> = mock()
    private lateinit var repository: AppRepository

    @Before
    fun setUp() {
        repository = AppRepositoryImplement(userSettingDataStore)
    }

    @Test
    fun returnsStateFirstTimeEnterApp_whenStateFirstTimeEnterAppIsSavedInDataStore() = runTest {
        whenever(preferences[PreferenceKey.FIRST_TIME_ENTER_APP]).thenReturn(true)
        whenever(userSettingDataStore.data).thenReturn(flowOf(preferences))

        repository.checkFirstTimeEnterApp().test {
            val result = awaitItem()
            assertEquals(true, result)
            awaitComplete()
        }
    }
}