package com.hoanglinhsama.client.presentation.viewmodel.state

import com.google.firebase.auth.PhoneAuthProvider
import com.hoanglinhsama.client.presentation.viewmodel.LoginMethod

data class LoginState(
    private val _listMethodLogin: List<LoginMethod>? = null,
    private val _isFocus: Boolean = false,
    private val _phoneNumber: String = "",
    private val _isCheckRemember: Boolean = false,
    private val _listCharacterOtp: List<String> = List(6) { "" },
    private val _messageSendVerCode: String = "",
    private val _timeOtpRemaining: Long = 60,
    private val _shouldStartCountdown: Boolean = false,
    private val _tokenResend: PhoneAuthProvider.ForceResendingToken? = null,
    private val _isShowBottomSheet: Boolean = false,
) {
    val listMethodLogin = _listMethodLogin
    val isFocus = _isFocus
    val phoneNumber = _phoneNumber
    val isCheckRemember = _isCheckRemember
    val listCharacterOtp = _listCharacterOtp
    val messageSendVerCode = _messageSendVerCode
    val timeOtpRemaining = _timeOtpRemaining
    val shouldStartCountdown = _shouldStartCountdown
    val tokenResend = _tokenResend
}