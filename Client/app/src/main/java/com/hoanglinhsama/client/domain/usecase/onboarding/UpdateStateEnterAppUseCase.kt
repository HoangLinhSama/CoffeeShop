package com.hoanglinhsama.client.domain.usecase.onboarding

import com.hoanglinhsama.client.domain.repository.OnboardingRepository
import javax.inject.Inject

class UpdateStateEnterAppUseCase @Inject constructor(private val onBoardingRepository: OnboardingRepository) {
    suspend operator fun invoke() {
        onBoardingRepository.updateStateEnterApp()
    }
}