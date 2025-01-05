package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.domain.usecase.GetDrinkCategoryUseCase
import com.hoanglinhsama.client.domain.usecase.GetDrinkUseCase
import com.hoanglinhsama.client.domain.usecase.GetPromotionUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.HomeEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPromotionUseCase: GetPromotionUseCase,
    private val getDrinkCategoryUseCase: GetDrinkCategoryUseCase,
    private val getDrinkUseCase: GetDrinkUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state = _state

    init {
        getPromotion()
        getDrinkCategory()
        getDrink()
    }

    private fun getDrinkCategory() {
        val itemsDrinkCategory = getDrinkCategoryUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsDrinkCategory = itemsDrinkCategory)
    }

    private fun getDrink() {
        val itemsDrink = getDrinkUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsDrink = itemsDrink)
    }

    private fun getPromotion() {
        val itemsVoucher = getPromotionUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsVoucher = itemsVoucher)
    }

    // TODO ("Deploy events on HomeScreen)
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DrinkCategoryClickEvent -> {
                _state.value = _state.value.copy(_selectedDrinkCategory = event.drinkCategoryIndex)
            }

            is HomeEvent.VoucherClickEvent -> {

            }

            is HomeEvent.AvatarClickEvent -> {

            }

            is HomeEvent.FavoriteClickEvent -> {

            }

            is HomeEvent.NotificationClickEvent -> {

            }

            is HomeEvent.QuickOderClickEvent -> {

            }

            is HomeEvent.PromotionAutoFlipperEvent -> {
                _state.value =
                    _state.value.copy(_currentPromotionIndex = event.currentPromotionIndex)
            }
        }
    }

}

