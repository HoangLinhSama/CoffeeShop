package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.domain.usecase.main.CreateTempOrderUseCase
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.common.UpdateDrinkOrderHolder
import com.hoanglinhsama.client.presentation.viewmodel.event.DetailDrinkEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.DetailDrinkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDrinkViewModel @Inject constructor(
    private val createTempOrderUseCase: CreateTempOrderUseCase,
) : ViewModel() {
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
                val listToppingChecked = _state.value.listToppingChecked.toMutableList()
                listToppingChecked[event.toppingIndex] = event.isChecked
                _state.value = _state.value.copy(_listToppingChecked = listToppingChecked)
            }

            is DetailDrinkEvent.DrinkCountEvent -> {
                _state.value = _state.value.copy(_countDrink = event.countDrink)
            }

            is DetailDrinkEvent.NoteOrderEvent -> {
                _state.value = _state.value.copy(_noteOrder = event.noteOrder)
            }

            is DetailDrinkEvent.OrderEvent -> {
                viewModelScope.launch {
                    createTempOrderUseCase(
                        event.id,
                        event.picture,
                        event.name,
                        event.size,
                        event.listTopping,
                        _state.value.noteOrder,
                        _state.value.countDrink,
                        event.totalPrice,
                        event.drinkCategory
                    ).collect {
                        TempOrderHolder.setTempOrder(it)
                    }
                }
            }

            is DetailDrinkEvent.NoteFocusEvent -> {
                _state.value = _state.value.copy(_isFocus = event.isFocus)
            }

            is DetailDrinkEvent.ReviewClickEvent -> {

            }

            is DetailDrinkEvent.SendUpdateDrinkOrderEvent -> {
                UpdateDrinkOrderHolder.setUpdateDrinkOrder(event.drink)
            }
        }
    }
}