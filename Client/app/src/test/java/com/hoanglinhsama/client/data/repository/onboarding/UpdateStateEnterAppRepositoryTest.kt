package com.hoanglinhsama.client.data.repository.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.hoanglinhsama.client.data.repository.OnBoardingRepositoryImplement
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
@Config(manifest = Config.NONE)
class UpdateStateEnterAppRepositoryTest {
    private lateinit var repository: OnBoardingRepositoryImplement
    private lateinit var userSettingDataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val testFile = File(context.filesDir, "test_update_state_enter_app.preferences_pb")
        testFile.delete()

        userSettingDataStore = PreferenceDataStoreFactory.create { testFile }

        repository = OnBoardingRepositoryImplement(
            mainApi = mock(),
            userSettingDataStore = userSettingDataStore,
        )
    }

    @Test
    fun firstTimeEnterApp_shouldSetToFalse() = runTest {
        userSettingDataStore.edit { it[PreferenceKey.FIRST_TIME_ENTER_APP] = true }

        repository.updateStateEnterApp()

        val preferences = userSettingDataStore.data.first()
        assertFalse(preferences[PreferenceKey.FIRST_TIME_ENTER_APP] == true)
    }
}