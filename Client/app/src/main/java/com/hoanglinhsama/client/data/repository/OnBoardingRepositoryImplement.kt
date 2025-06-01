package com.hoanglinhsama.client.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hoanglinhsama.client.data.source.paging.OnboardingPagingSource
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Onboarding
import com.hoanglinhsama.client.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImplement @Inject constructor(
    private val userSettingDataStore: DataStore<Preferences>,
    private val mainApi: MainApi,
) :
    OnboardingRepository {
    override suspend fun updateStateEnterApp() {
        userSettingDataStore.edit {
            it[PreferenceKey.FIRST_TIME_ENTER_APP] = false
        }
    }

    override fun getOnboarding(): Flow<PagingData<Onboarding>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 3, pageSize = 3),
            pagingSourceFactory = {
                OnboardingPagingSource(mainApi = mainApi)
            }).flow
    }
}