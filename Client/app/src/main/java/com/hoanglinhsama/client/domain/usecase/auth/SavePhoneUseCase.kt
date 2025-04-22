package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class SavePhoneUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String, callback: () -> Unit) {
        authRepository.savePhone(phoneNumber, callback)
    }
}