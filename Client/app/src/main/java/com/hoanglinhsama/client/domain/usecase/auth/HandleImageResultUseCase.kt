package com.hoanglinhsama.client.domain.usecase.auth

import androidx.activity.result.ActivityResult
import com.hoanglinhsama.client.domain.repository.AuthRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class HandleImageResultUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        activityResult: ActivityResult,
        callback: (MultipartBody.Part) -> Unit,
    ) {
        authRepository.handleImageResult(activityResult, callback)
    }
}