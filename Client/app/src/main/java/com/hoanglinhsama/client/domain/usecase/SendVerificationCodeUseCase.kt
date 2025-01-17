package com.hoanglinhsama.client.domain.usecase

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        activity: Activity,
        phoneNumber: String,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        authRepository.sendVerificationCode(activity, phoneNumber, callback)
    }
}