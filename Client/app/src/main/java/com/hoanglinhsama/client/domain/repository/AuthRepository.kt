package com.hoanglinhsama.client.domain.repository

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.paging.PagingData
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Policies
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AuthRepository {
    suspend fun sendVerificationCode(
        activity: Activity,
        phoneNumber: String,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )

    suspend fun verifyCode(
        verificationId: String,
        code: String,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )

    suspend fun resendOtp(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    )

    fun getPolicy(): Flow<PagingData<Policies>>

    fun uploadAvatar(
        multipartBody: MultipartBody.Part,
    ): Flow<Result<String>>

    fun signup(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        image: String,
    ): Flow<Result<Unit>>

    fun checkHadAccount(
        phone: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Unit>

    suspend fun savePhone(phone: String, callback: () -> Unit)
    suspend fun updateStateRememberPhone(isRemember: Boolean)
    fun autoFillPhone(): Flow<Boolean>
}