package com.hoanglinhsama.client.data.repository.main

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class LogOutRepositoryTest {
    private lateinit var repository: MainRepositoryImplement
    private lateinit var userSettingDataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val testFile = File(context.filesDir, "test_logged_in.preferences_pb")
        testFile.delete()

        userSettingDataStore = PreferenceDataStoreFactory.create { testFile }

        repository = MainRepositoryImplement(
            mainApi = mock(),
            userSettingDataStore = userSettingDataStore,
            httpProvider = mock()
        )
    }

    @Test
    fun logOut_shouldSetLOGGEDINToFalse() = runTest {
        userSettingDataStore.edit { it[PreferenceKey.LOGGED_IN] = true }

        repository.logOut()

        val preferences = userSettingDataStore.data.first()
        assertFalse(preferences[PreferenceKey.LOGGED_IN] == true)
    }
}