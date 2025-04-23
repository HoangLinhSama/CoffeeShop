package com.hoanglinhsama.client.presentation.viewmodel.event

sealed class DetailDrinkEvent {
    data class ReadMoreDescriptionClickEvent(val isExpanded: Boolean) : DetailDrinkEvent()
    data class SizeSelectedEvent(val indexSize: Int) : DetailDrinkEvent()
    data class FavoriteClickEvent(val isFavorite: Boolean) : DetailDrinkEvent()
    object ShareClickEvent : DetailDrinkEvent()
    data class ToppingCheckEvent(val toppingIndex: Int, val isChecked: Boolean) : DetailDrinkEvent()
    data class DrinkCountEvent(val countDrink: Int) : DetailDrinkEvent()
    data class NoteOrderEvent(val noteOrder: String) : DetailDrinkEvent()
    object OrderEvent : DetailDrinkEvent()
    data class NoteFocusEvent(val isFocus: Boolean) : DetailDrinkEvent()
    data class ReviewClickEvent(val drinkId: Int) : DetailDrinkEvent()
}