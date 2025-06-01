package com.hoanglinhsama.client.presentation.viewmodel.event

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.hoanglinhsama.client.data.model.Result
import okhttp3.MultipartBody

sealed class SignupEvent {
    data class CheckPermissionEvent(
        val activityResultLauncher: ActivityResultLauncher<Intent>,
        val requestPermissionLauncher: ActivityResultLauncher<String>,
        val context: Context,
    ) : SignupEvent()

    data class UpdateFirstNameEvent(val firstName: String) : SignupEvent()
    data class UpdateLastNameEvent(val lastName: String) : SignupEvent()
    data class UpdateAddressEvent(val address: String) : SignupEvent()
    data class FocusChangeEvent(val index: Int, val isFocus: Boolean) : SignupEvent()
    data class ButtonSignupClickEvent(val phone: String) : SignupEvent()
    data class CheckBoxCheckEvent(val isChecked: Boolean) : SignupEvent()
    data class HandleImageResultEvent(
        val activityResult: ActivityResult,
        val context: Context,
        val callback: (MultipartBody.Part) -> Unit,
    ) : SignupEvent()

    object ReadPolicyEvent : SignupEvent()
    data class UploadAvatarEvent(
        val multipartBody: MultipartBody.Part,
    ) : SignupEvent()

    data class UpdateResultSignup(val result: Result<Unit>) : SignupEvent()
    data class SavePhoneEvent(val phone: String, val callback: () -> Unit) : SignupEvent()
}
