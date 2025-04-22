package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class OtherEvent {
    data class FeatureItemClickEvent(val index: Int) : OtherEvent()
}