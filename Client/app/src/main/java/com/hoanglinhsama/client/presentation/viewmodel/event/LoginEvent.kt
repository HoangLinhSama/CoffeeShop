package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class LoginEvent {
    data class TextFieldFocusEvent(val index: Int, val isFocus: Boolean) : LoginEvent()
    data class LoginMethodClickEvent(val index: Int) : LoginEvent()
    data class PhoneNumberEvent(val phoneNumber: String) : LoginEvent()
    data class PasswordEvent(val password: String) : LoginEvent()
    data class ShowPasswordEvent(val isShowPassword: Boolean) : LoginEvent()
    object ButtonLoginEvent : LoginEvent()
    data class RememberPasswordEvent(val isCheckRemember: Boolean) : LoginEvent()
}