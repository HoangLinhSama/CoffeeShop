package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class OnBoardingEvent {
    object UpdateStateEnterAppEvent : OnBoardingEvent()
}