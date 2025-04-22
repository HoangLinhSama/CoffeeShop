package com.hoanglinhsama.client.domain.usecase.auth

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class ResendOtpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        authRepository.resendOtp(activity, phoneNumber, token, callback)
    }
}