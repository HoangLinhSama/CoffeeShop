package com.hoanglinhsama.client.domain.usecase.auth

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        multipartBody: MultipartBody.Part,
    ): Flow<Result<String>> {
        return authRepository.uploadAvatar(multipartBody)
    }
}