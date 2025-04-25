package com.hoanglinhsama.client.presentation.viewmodel.event

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider

sealed class LoginEvent {
    data class TextFieldFocusEvent(val isFocus: Boolean) : LoginEvent()
    data class LoginMethodClickEvent(val index: Int) : LoginEvent()
    data class PhoneNumberEvent(val phoneNumber: String) : LoginEvent()
    data class VerifyCodeEvent(
        val verificationId: String,
        val code: String,
        val callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) : LoginEvent()

    data class RememberPhoneEvent(val isCheckRemember: Boolean) : LoginEvent()
    data class OtpEnterEvent(val index: Int, val otpCharacter: String) : LoginEvent()
    data class ButtonLoginClickEvent(
        val activity: Activity,
        val phoneNumber: String,
        val callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) : LoginEvent()

    data class MessageSendVerCodeEvent(val message: String) : LoginEvent()
    data class TimeOtpRemainingEvent(val time: Long) : LoginEvent()
    data class ShouldStartCountdownEvent(val shouldStartCountdown: Boolean) : LoginEvent()
    data class ResendingOtpEvent(
        val activity: Activity,
        val phoneNumber: String,
        val token: PhoneAuthProvider.ForceResendingToken,
        val callback: (Boolean, String?, PhoneAuthProvider.ForceResendingToken?) -> Unit,
    ) : LoginEvent()

    data class TokenResendEvent(val token: PhoneAuthProvider.ForceResendingToken? = null) :
        LoginEvent()

    data class CheckHadAccountEvent(
        val phoneNumber: String,
        val callback: (String, Boolean?) -> Unit,
    ) :
        LoginEvent()

    data class SavePhoneEvent(val phoneNumber: String, val callback: () -> Unit) : LoginEvent()
}