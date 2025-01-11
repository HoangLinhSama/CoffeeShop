package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.LoginMethod
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.LightAzure
import com.hoanglinhsama.client.presentation.view.ui.theme.TrueBlue
import com.hoanglinhsama.client.presentation.viewmodel.event.LoginEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    init {
        initDataMethodLogin()
    }

    private fun initDataMethodLogin() {
        val listMethodLogin = listOf(
            LoginMethod(R.drawable.ic_email, "Email", Color.White, DarkCharcoal2),
            LoginMethod(R.drawable.ic_google, "Google", Color.White, DarkCharcoal2),
            LoginMethod(R.drawable.ic_facebook, "Facebook", TrueBlue, Color.White),
            LoginMethod(R.drawable.ic_twitter, "Twitter", LightAzure, Color.White)
        )
        _state.value = _state.value.copy(_listMethodLogin = listMethodLogin)
    }

    // TODO ("Deploy events on LoginScreen)
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.TextFieldFocusEvent -> {
                val listIsFocus = _state.value.listIsFocus.toMutableList()
                listIsFocus[event.index] = event.isFocus
                _state.value = _state.value.copy(_listIsFocus = listIsFocus)
            }

            is LoginEvent.LoginMethodClickEvent -> {

            }

            is LoginEvent.PasswordEvent -> {
                _state.value = _state.value.copy(_password = event.password)
            }

            is LoginEvent.PhoneNumberEvent -> {
                _state.value = _state.value.copy(_phoneNumber = event.phoneNumber)
            }

            is LoginEvent.ShowPasswordEvent -> {
                _state.value = _state.value.copy(_isShowPassword = event.isShowPassword)
            }

            LoginEvent.ButtonLoginEvent -> {

            }

            is LoginEvent.RememberPasswordEvent -> {
                _state.value=state.value.copy(_isCheckRemember = event.isCheckRemember)
            }
        }
    }
}