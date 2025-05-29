package com.hoanglinhsama.client.domain.usecase.auth

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import javax.inject.Inject

class CheckPermissionUseCase @Inject constructor(
    private val intent: Intent,
    private val context: Context,
) {
    operator fun invoke(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        requestPermissionLauncher: ActivityResultLauncher<String>,
    ) {
        if (ContextCompat.checkSelfPermission(
                context, READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) activityResultLauncher.launch(intent)
        else requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
    }
}