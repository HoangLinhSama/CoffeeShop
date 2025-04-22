package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.auth.CheckPermissionUseCase
import com.hoanglinhsama.client.domain.usecase.auth.GetPolicyUseCase
import com.hoanglinhsama.client.domain.usecase.auth.HandleImageResultUseCase
import com.hoanglinhsama.client.domain.usecase.auth.SavePhoneUseCase
import com.hoanglinhsama.client.domain.usecase.auth.SignupUseCase
import com.hoanglinhsama.client.domain.usecase.auth.UploadAvatarUseCase
import com.hoanglinhsama.client.presentation.view.screen.formatPhoneNumberToE164
import com.hoanglinhsama.client.presentation.viewmodel.event.SignupEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val checkPermissionUseCase: CheckPermissionUseCase,
    private val handleImageResultUseCase: HandleImageResultUseCase,
    private val getPolicyUseCase: GetPolicyUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val signupUseCase: SignupUseCase,
    private val savePhoneUseCase: SavePhoneUseCase,
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
                checkPermissionUseCase(
                    event.activityResultLauncher, event.requestPermissionLauncher
                )
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
                handleImageResultUseCase(event.activityResult, event.callback)

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
