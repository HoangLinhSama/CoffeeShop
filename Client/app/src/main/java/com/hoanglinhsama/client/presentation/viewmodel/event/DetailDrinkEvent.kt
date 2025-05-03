package com.hoanglinhsama.client.presentation.viewmodel.event

import com.hoanglinhsama.client.domain.model.Drink

sealed class DetailDrinkEvent {
    data class ReadMoreDescriptionClickEvent(val isExpanded: Boolean) : DetailDrinkEvent()
    data class SizeSelectedEvent(val indexSize: Int) : DetailDrinkEvent()
    data class FavoriteClickEvent(val isFavorite: Boolean) : DetailDrinkEvent()
    object ShareClickEvent : DetailDrinkEvent()
    data class ToppingCheckEvent(val toppingIndex: Int, val isChecked: Boolean) : DetailDrinkEvent()
    data class DrinkCountEvent(val countDrink: Int) : DetailDrinkEvent()
    data class NoteOrderEvent(val noteOrder: String) : DetailDrinkEvent()
    data class OrderEvent(
        val id: Int,
        val picture: String,
        val name: String,
        val totalPrice: Float,
        val size: String?,
        val listTopping: List<String>?,
    ) :
        DetailDrinkEvent()

    data class NoteFocusEvent(val isFocus: Boolean) : DetailDrinkEvent()
    data class ReviewClickEvent(val drinkId: Int) : DetailDrinkEvent()
    data class SendUpdateDrinkOrderEvent(val drink: Drink) : DetailDrinkEvent()
}