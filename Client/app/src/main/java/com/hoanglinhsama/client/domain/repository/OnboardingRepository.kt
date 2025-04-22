package com.hoanglinhsama.client.domain.repository

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Onboarding
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun updateStateEnterApp()
    fun getOnboarding(): Flow<PagingData<Onboarding>>
}
