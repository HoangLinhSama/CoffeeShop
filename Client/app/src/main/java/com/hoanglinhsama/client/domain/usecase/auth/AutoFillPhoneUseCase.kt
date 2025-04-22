package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoFillPhoneUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.autoFillPhone()
    }
}