package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class ShopEvent {
    data class UpdateSearchShopEvent(val value: String) : ShopEvent()
    data class OnSearchClickEvent(val searchText: String) : ShopEvent()
    object OnFilterClickEvent : ShopEvent()
}