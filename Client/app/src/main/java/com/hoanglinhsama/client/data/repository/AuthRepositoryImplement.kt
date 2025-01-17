package com.hoanglinhsama.client.data.repository

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.domain.repository.AuthRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImplement @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override suspend fun sendVerificationCode(
        activity: Activity,
        phoneNumber: String,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        firebaseAuth.setLanguageCode("vi")
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    callback(true, null, null)
                    signInWithPhoneAuthCredential(p0, callback)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    callback(false, p0.message, null)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    callback(true, verificationId, token)
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyCode(
        verificationId: String,
        code: String,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential, callback)
    }

    override suspend fun resendOtp(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    callback(true, null, null)
                    signInWithPhoneAuthCredential(p0, callback)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    callback(false, p0.message, null)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    callback(true, verificationId, token)
                }
            }).setForceResendingToken(token).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    callback(true, user?.uid, null)
                } else {
                    callback(false, task.exception?.message, null)
                }
            }
    }
}