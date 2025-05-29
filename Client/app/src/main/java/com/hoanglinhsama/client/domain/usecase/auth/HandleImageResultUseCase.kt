package com.hoanglinhsama.client.domain.usecase.auth

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResult
import com.hoanglinhsama.client.domain.repository.AuthRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class HandleImageResultUseCase @Inject constructor(private val context: Context) {
    operator fun invoke(
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
}