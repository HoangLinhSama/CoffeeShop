package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateStateRememberPhoneUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(isRemember: Boolean) {
        authRepository.updateStateRememberPhone(isRemember)
    }
}