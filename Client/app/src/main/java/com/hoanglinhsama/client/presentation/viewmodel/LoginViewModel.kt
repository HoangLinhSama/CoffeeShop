package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.LoginMethod
import com.hoanglinhsama.client.domain.usecase.ResendOtpUseCase
import com.hoanglinhsama.client.domain.usecase.SendVerificationCodeUseCase
import com.hoanglinhsama.client.domain.usecase.VerifyCodeUseCase
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.LightAzure
import com.hoanglinhsama.client.presentation.view.ui.theme.TrueBlue
import com.hoanglinhsama.client.presentation.viewmodel.event.LoginEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val resendOtpUseCase: ResendOtpUseCase,
) : ViewModel() {
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
                _state.value = _state.value.copy(_isFocus = event.isFocus)
            }

            is LoginEvent.LoginMethodClickEvent -> {

            }

            is LoginEvent.PhoneNumberEvent -> {
                _state.value = _state.value.copy(_phoneNumber = event.phoneNumber)
            }

            is LoginEvent.RememberPhoneEvent -> {
                _state.value = state.value.copy(_isCheckRemember = event.isCheckRemember)
            }

            is LoginEvent.VerifyCodeEvent -> {
                viewModelScope.launch {
                    verifyCodeUseCase(
                        verificationId = event.verificationId,
                        code = event.code,
                        callback = event.callback
                    )
                }
            }

            is LoginEvent.OtpEnterEvent -> {
                val listCharacterOtp = _state.value.listCharacterOtp.toMutableList()
                listCharacterOtp[event.index] = event.otpCharacter
                _state.value = _state.value.copy(_listCharacterOtp = listCharacterOtp)
            }

            is LoginEvent.ButtonLoginClickEvent -> {
                viewModelScope.launch {
                    sendVerificationCodeUseCase(event.activity, event.phoneNumber, event.callback)
                }
            }

            is LoginEvent.MessageSendVerCodeEvent -> {
                _state.value = _state.value.copy(_messageSendVerCode = event.message)
            }

            is LoginEvent.TimeOtpRemainingEvent -> {
                _state.value = _state.value.copy(_timeOtpRemaining = event.time)
            }

            is LoginEvent.ShouldStartCountdownEvent -> {
                _state.value = _state.value.copy(_shouldStartCountdown = event.shouldStartCountdown)
            }

            is LoginEvent.ResendingOtpEvent -> {
                viewModelScope.launch {
                    resendOtpUseCase(event.activity, event.phoneNumber, event.token, event.callback)
                }
            }

            is LoginEvent.TokenResendEvent -> {
                _state.value = _state.value.copy(_tokenResend = event.token)
            }
        }
    }
}