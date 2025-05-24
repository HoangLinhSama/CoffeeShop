package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}