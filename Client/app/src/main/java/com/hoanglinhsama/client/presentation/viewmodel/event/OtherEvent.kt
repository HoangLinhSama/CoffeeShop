package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class OtherEvent {
    object LogoutEvent : OtherEvent()
    object ShowQrCodeEvent : OtherEvent()
}