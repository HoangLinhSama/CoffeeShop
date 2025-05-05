package com.hoanglinhsama.client.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hoanglinhsama.client.data.source.paging.preferences.PreferenceKey
import com.hoanglinhsama.client.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImplement @Inject constructor(private val userSettingDataStore: DataStore<Preferences>) :
    AppRepository {
    override fun checkFirstTimeEnterApp(): Flow<Boolean> {
        return userSettingDataStore.data.map {
            it[PreferenceKey.FIRST_TIME_ENTER_APP] != false
        }
    }

    override fun checkSignedIn(): Flow<Boolean> {
        return userSettingDataStore.data.map {
            it[PreferenceKey.LOGGED_IN] == true
        }
    }
}