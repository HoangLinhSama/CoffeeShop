package com.hoanglinhsama.client.domain.usecase.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.hoanglinhsama.client.domain.repository.AuthRepository
import javax.inject.Inject

class CheckPermissionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        requestPermissionLauncher: ActivityResultLauncher<String>,
    ) {
        authRepository.checkPermission(activityResultLauncher, requestPermissionLauncher)
    }
}