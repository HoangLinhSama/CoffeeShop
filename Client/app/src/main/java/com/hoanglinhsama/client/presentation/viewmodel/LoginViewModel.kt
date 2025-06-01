package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.usecase.auth.AutoFillPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.auth.CheckHadAccountUseCase
import com.hoanglinhsama.client.domain.usecase.auth.SavePhoneUseCase
import com.hoanglinhsama.client.domain.usecase.auth.UpdateStateRememberPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.presentation.view.screen.revertE164ToPhoneNumber
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.LightAzure
import com.hoanglinhsama.client.presentation.view.ui.theme.TrueBlue
import com.hoanglinhsama.client.presentation.viewmodel.event.LoginEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkHadAccountUseCase: CheckHadAccountUseCase,
    private val savePhoneUseCase: SavePhoneUseCase,
    private val updateStateRememberPhoneUseCase: UpdateStateRememberPhoneUseCase,
    private val autoFillPhoneUseCase: AutoFillPhoneUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    init {
        initDataMethodLogin()
        autoFillPhone()
    }

    private fun autoFillPhone() {
        viewModelScope.launch {
            autoFillPhoneUseCase().first().let {
                _state.value = _state.value.copy(_isCheckRemember = it)
                if (it) {
                    getPhoneUseCase().collect {
                        _state.value =
                            _state.value.copy(_phoneNumber = revertE164ToPhoneNumber(it, "+84"))
                    }
                }
            }
        }

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
                viewModelScope.launch {
                    updateStateRememberPhoneUseCase(event.isCheckRemember)
                }
            }

            is LoginEvent.VerifyCodeEvent -> {
                viewModelScope.launch {
                    val credential =
                        PhoneAuthProvider.getCredential(event.verificationId, event.code)
                    signInWithPhoneAuthCredential(credential, event.callback)
                }
            }

            is LoginEvent.OtpEnterEvent -> {
                val listCharacterOtp = _state.value.listCharacterOtp.toMutableList()
                listCharacterOtp[event.index] = event.otpCharacter
                _state.value = _state.value.copy(_listCharacterOtp = listCharacterOtp)
            }

            is LoginEvent.ButtonLoginClickEvent -> {
                viewModelScope.launch {
                    firebaseAuth.setLanguageCode("vi")
                    val options =
                        PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(event.phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS).setActivity(event.activity)
                            .setCallbacks(object :
                                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                                    event.callback(true, null, null)
                                    signInWithPhoneAuthCredential(p0, event.callback)
                                }

                                override fun onVerificationFailed(p0: FirebaseException) {
                                    event.callback(false, p0.message, null)
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    event.callback(true, verificationId, token)
                                }
                            }).build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
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
                    val options =
                        PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(event.phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS).setActivity(event.activity)
                            .setCallbacks(object :
                                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                                    event.callback(true, null, null)
                                    signInWithPhoneAuthCredential(p0, event.callback)
                                }

                                override fun onVerificationFailed(p0: FirebaseException) {
                                    event.callback(false, p0.message, null)
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    event.callback(true, verificationId, token)
                                }
                            }).setForceResendingToken(event.token).build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }
            }

            is LoginEvent.TokenResendEvent -> {
                _state.value = _state.value.copy(_tokenResend = event.token)
            }

            is LoginEvent.CheckHadAccountEvent -> {
                viewModelScope.launch {
                    checkHadAccountUseCase(event.phoneNumber, event.callback).collect {

                    }
                }
            }

            is LoginEvent.SavePhoneEvent -> {
                viewModelScope.launch {
                    savePhoneUseCase(event.phoneNumber, event.callback)
                }
            }
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                callback(true, user?.uid, null)
            } else {
                callback(false, task.exception?.message, null)
            }
        }
    }
}

data class LoginMethod(
    private val _icon: Int,
    private val _title: String,
    private val _containerColor: Color,
    private val _contentColor: Color,
) {
    val icon = _icon
    val title = _title
    val containerColor = _containerColor
    val contentColor = _contentColor
}
