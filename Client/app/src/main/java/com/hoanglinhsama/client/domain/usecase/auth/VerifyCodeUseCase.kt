package com.hoanglinhsama.client.domain.usecase.auth

import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        verificationId: String,
        code: String,
        callback: (Boolean, String?,PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        authRepository.verifyCode(verificationId, code, callback)
    }
}