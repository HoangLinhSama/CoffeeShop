package com.hoanglinhsama.client.domain.usecase.onboarding

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Onboarding
import com.hoanglinhsama.client.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingUseCase @Inject constructor(private val onboardingRepository: OnboardingRepository) {
    operator fun invoke(): Flow<PagingData<Onboarding>> {
        return onboardingRepository.getOnboarding()
    }
}