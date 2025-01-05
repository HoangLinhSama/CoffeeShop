package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.User

sealed class HomeEvent {
    object NotificationClickEvent : HomeEvent()
    data class FavoriteClickEvent(val userId: Int) : HomeEvent()
    data class AvatarClickEvent(val user: User) : HomeEvent()
    object VoucherClickEvent : HomeEvent()
    data class QuickOderClickEvent(val drink: Drink) : HomeEvent()
    data class DrinkCategoryClickEvent(val drinkCategoryName: String, val drinkCategoryIndex: Int) :
        HomeEvent()

    data class PromotionAutoFlipperEvent(val currentPromotionIndex: Int) : HomeEvent()
}