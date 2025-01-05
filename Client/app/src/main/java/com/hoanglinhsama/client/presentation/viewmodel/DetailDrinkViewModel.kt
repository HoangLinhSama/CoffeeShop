package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.presentation.viewmodel.event.DetailDrinkEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.DetailDrinkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailDrinkViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(DetailDrinkState())
    val state = _state

    // TODO ("Deploy events on DetailDrinkScreen)
    fun onEvent(event: DetailDrinkEvent) {
        when (event) {
            is DetailDrinkEvent.ReadMoreDescriptionClickEvent -> {
                _state.value = _state.value.copy(_isExpanded = event.isExpanded)
            }

            is DetailDrinkEvent.SizeSelectedEvent -> {
                _state.value = _state.value.copy(_indexSizeSelected = event.indexSize)
            }

            is DetailDrinkEvent.FavoriteClickEvent -> {
                _state.value = _state.value.copy(_isFavorite = event.isFavorite)
            }

            is DetailDrinkEvent.ShareClickEvent -> {

            }

            is DetailDrinkEvent.ToppingCheckEvent -> {
                val newListToppingChecked = _state.value.listToppingChecked.toMutableList()
                newListToppingChecked[event.toppingIndex] = event.isChecked
                _state.value = _state.value.copy(_listToppingChecked = newListToppingChecked)
            }

            is DetailDrinkEvent.DrinkCountEvent -> {
                _state.value = _state.value.copy(_countDrink = event.countDrink)
            }

            is DetailDrinkEvent.NoteOrderEvent -> {
                _state.value = _state.value.copy(_noteOrder = event.noteOrder)
            }

            DetailDrinkEvent.OrderEvent -> {

            }
        }
    }
}