package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        image: String,
    ): Flow<Result<Unit>> {
        return authRepository.signup(firstName, lastName, phone, address, image)
    }
}