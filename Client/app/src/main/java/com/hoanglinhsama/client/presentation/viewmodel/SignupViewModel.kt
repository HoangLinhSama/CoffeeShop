package com.hoanglinhsama.client.presentation.viewmodel

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.auth.GetPolicyUseCase
import com.hoanglinhsama.client.domain.usecase.auth.SavePhoneUseCase
import com.hoanglinhsama.client.domain.usecase.auth.SignupUseCase
import com.hoanglinhsama.client.domain.usecase.auth.UploadAvatarUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.SignupEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val getPolicyUseCase: GetPolicyUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val signupUseCase: SignupUseCase,
    private val savePhoneUseCase: SavePhoneUseCase,
    private val intent: Intent,
) : ViewModel() {
    private val _state = mutableStateOf(SignupState())
    val state = _state

    init {
        getPolicies()
    }

    private fun getPolicies() {
        val itemsPolicy = getPolicyUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsPolicy = itemsPolicy)
    }

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.UpdateAddressEvent -> {
                _state.value = _state.value.copy(_address = event.address)
            }

            is SignupEvent.CheckPermissionEvent -> {
                if (ContextCompat.checkSelfPermission(
                        event.context, READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) event.activityResultLauncher.launch(intent)
                else event.requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            }

            is SignupEvent.UpdateFirstNameEvent -> {
                _state.value = _state.value.copy(_firstName = event.firstName)
            }

            is SignupEvent.UpdateLastNameEvent -> {
                _state.value = _state.value.copy(_lastName = event.lastName)
            }

            is SignupEvent.FocusChangeEvent -> {
                val listFocus = _state.value.listTextFieldFocus.toMutableList()
                listFocus[event.index] = event.isFocus
                _state.value = _state.value.copy(_listTextFieldFocus = listFocus)
            }

            is SignupEvent.ButtonSignupClickEvent -> {
                _state.value = _state.value.copy(
                    _signupResultFlow =
                        signupUseCase(
                            _state.value.firstName,
                            _state.value.lastName,
                            event.phone,
                            _state.value.address,
                            _state.value.uriAvatar
                        )
                )
            }

            is SignupEvent.CheckBoxCheckEvent -> {
                _state.value = _state.value.copy(_isChecked = event.isChecked)

            }

            is SignupEvent.HandleImageResultEvent -> {
                if (event.activityResult.resultCode == Activity.RESULT_OK && event.activityResult.data != null) {
                    val uri = event.activityResult.data?.data ?: return
                    val inputStream = event.context.contentResolver.openInputStream(uri)
                    val requestBody =
                        inputStream?.readBytes()
                            ?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val fileName = "avatar_${System.currentTimeMillis()}.jpg"
                    requestBody?.let {
                        event.callback(
                            MultipartBody.Part.createFormData(
                                "pictureAvatar",
                                fileName,
                                it
                            )
                        )
                    }
                }
            }

            is SignupEvent.ReadPolicyEvent -> {
                _state.value = _state.value.copy(_isReadPolicy = true)
            }

            is SignupEvent.UploadAvatarEvent -> {
                viewModelScope.launch {
                    uploadAvatarUseCase(event.multipartBody).collect {
                        if (it is Result.Success) _state.value =
                            _state.value.copy(_uriAvatar = it.data)
                    }
                }
            }

            is SignupEvent.UpdateResultSignup -> {
                _state.value =
                    _state.value.copy(_resultSignup = event.result)
            }

            is SignupEvent.SavePhoneEvent -> {
                viewModelScope.launch {
                    savePhoneUseCase(event.phone, event.callback)
                }
            }
        }
    }
}
