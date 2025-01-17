package com.hoanglinhsama.client.domain.repository

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {
    suspend fun sendVerificationCode(
        activity: Activity,
        phoneNumber: String,
        callback: (Boolean, String?,PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )

    suspend fun verifyCode(
        verificationId: String,
        code: String,
        callback: (Boolean, String?,PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )

    suspend fun resendOtp(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        callback: (Boolean, String?,PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )
}