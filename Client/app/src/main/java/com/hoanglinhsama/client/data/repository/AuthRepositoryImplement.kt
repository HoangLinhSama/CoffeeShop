package com.hoanglinhsama.client.data.repository

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.source.paging.PolicyPagingSource
import com.hoanglinhsama.client.data.source.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.remote.api.ApiUtil
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Policies
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImplement @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val intent: Intent,
    private val context: Context,
    private val mainApi: MainApi,
    private val userSettingDataStore: DataStore<Preferences>,
) : AuthRepository {
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

    override fun checkPermission(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        requestPermissionLauncher: ActivityResultLauncher<String>,
    ) {
        if (ContextCompat.checkSelfPermission(
                context, READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) activityResultLauncher.launch(intent)
        else requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
    }

    override fun handleImageResult(
        activityResult: ActivityResult,
        callback: (MultipartBody.Part) -> Unit,
    ) {
        if (activityResult.resultCode == Activity.RESULT_OK && activityResult.data != null) {
            val uri = activityResult.data?.data ?: return
            val inputStream = context.contentResolver.openInputStream(uri)
            val requestBody =
                inputStream?.readBytes()?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val fileName = "avatar_${System.currentTimeMillis()}.jpg"
            requestBody?.let {
                callback(
                    MultipartBody.Part.createFormData(
                        "pictureAvatar",
                        fileName,
                        it
                    )
                )
            }
        }
    }

    override fun getPolicy(): Flow<PagingData<Policies>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 4, pageSize = 4),
            pagingSourceFactory = {
                PolicyPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun uploadAvatar(
        multipartBody: MultipartBody.Part,
    ): Flow<Result<String>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.upLoadAvatar(multipartBody)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        emit(
                            Result.Success(
                                ApiUtil.BASE_URL + "picture/avatar/" + response.body()?.result?.get(
                                    0
                                )
                            )
                        )
                    } else if (response.body()?.status == "fail") {
                        emit(
                            Result.Error(
                                Exception(
                                    response.body()?.status
                                )
                            )
                        )
                    } else {
                        emit(Result.Error(Exception(response.body()?.status)))
                    }
                } else {
                    emit(Result.Error(Exception(("API request failed with status: ${response.code()}"))))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    override fun signup(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        image: String,
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.signup(firstName, lastName, phone, address, image)
                if (response.isSuccessful) {
                    if (response.body() == "success") {
                        emit(Result.Success(Unit))
                    } else if (response.body() == "fail") {
                        emit(Result.Error(Exception(response.body())))
                    } else {
                        emit(Result.Error(Exception(response.body())))
                    }
                } else {
                    emit(Result.Error(Exception(("API request failed with status: ${response.code()}"))))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    override fun checkHadAccount(
        phone: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Unit> {
        return flow {
            try {
                val response = mainApi.checkHadAccount(phone)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.let {
                            callback(response.body()?.status ?: "", it)
                        }
                    } else if (response.body()?.status == "fail") {
                        callback(response.body()?.status ?: "", null)
                    } else {
                        callback(response.body()?.status ?: "", null)
                    }
                } else {
                    callback("API request failed with status: ${response.code()}", null)
                }
            } catch (e: Exception) {
                callback("${e.message}", null)
            }
        }
    }

    override suspend fun savePhone(phone: String, callback: () -> Unit) {
        userSettingDataStore.edit {
            it[PreferenceKey.PHONE] = phone
        }
        callback()
    }

    override suspend fun updateStateRememberPhone(isRemember: Boolean) {
        userSettingDataStore.edit {
            it[PreferenceKey.CHECKED_SAVE_PHONE] = isRemember
        }
    }

    override fun autoFillPhone(): Flow<Boolean> {
        return userSettingDataStore.data.map {
            it[PreferenceKey.CHECKED_SAVE_PHONE] == true
        }
    }

    private fun signInWithPhoneAuthCredential(
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