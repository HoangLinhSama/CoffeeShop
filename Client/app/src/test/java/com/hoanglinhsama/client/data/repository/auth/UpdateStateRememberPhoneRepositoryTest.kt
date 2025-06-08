package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import junit.framework.TestCase.assertTrue
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
class UpdateStateRememberPhoneRepositoryTest {
    private lateinit var repository: AuthRepositoryImplement
    private lateinit var userSettingDataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val testFile = File(context.filesDir, "test_remember_phone_in.preferences_pb")
        testFile.delete()

        userSettingDataStore = PreferenceDataStoreFactory.create { testFile }

        repository = AuthRepositoryImplement(
            mainApi = mock(),
            userSettingDataStore = userSettingDataStore,
        )
    }

    @Test
    fun updateStateRememberPhone_shouldSetCHECKEDSAVEPHONE() = runTest {
        repository.updateStateRememberPhone(true)

        val preferences = userSettingDataStore.data.first()
        assertTrue(preferences[PreferenceKey.CHECKED_SAVE_PHONE] == true)
    }
}