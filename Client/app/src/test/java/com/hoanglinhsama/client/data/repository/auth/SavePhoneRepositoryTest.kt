package com.hoanglinhsama.client.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
class SavePhoneRepositoryTest {
    private lateinit var repository: AuthRepositoryImplement
    private lateinit var userSettingDataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val testFile = File(context.filesDir, "test_save_phone.preferences_pb")
        testFile.delete()

        userSettingDataStore = PreferenceDataStoreFactory.create { testFile }

        repository = AuthRepositoryImplement(
            mainApi = mock(),
            userSettingDataStore = userSettingDataStore,
        )
    }

    @Test
    fun shouldStorePhoneNumber_andInvokeCallback() = runTest {
        val phone = "+84968674274"
        var isCallbackCalled = false

        userSettingDataStore.edit { it[PreferenceKey.PHONE] = phone }

        repository.savePhone(phone) {
            isCallbackCalled = true
        }

        val preferences = userSettingDataStore.data.first()
        assertEquals(preferences[PreferenceKey.PHONE], phone)
        assertTrue(isCallbackCalled)
    }
}