package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckHadAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(phoneNumber: String, callback: (String, Boolean?) -> Unit): Flow<Result<Unit>> {
        return authRepository.checkHadAccount(phoneNumber,callback)
    }
}