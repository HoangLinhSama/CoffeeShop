package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.usecase.main.GetDrinkCategoryUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetDrinkUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetPhoneUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetPromotionUseCase
import com.hoanglinhsama.client.domain.usecase.main.GetUserUseCase
import com.hoanglinhsama.client.domain.usecase.main.UpdateStateLoginUseCase
import com.hoanglinhsama.client.presentation.viewmodel.common.VoucherHolder
import com.hoanglinhsama.client.presentation.viewmodel.event.HomeEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPromotionUseCase: GetPromotionUseCase,
    private val getDrinkCategoryUseCase: GetDrinkCategoryUseCase,
    private val getDrinkUseCase: GetDrinkUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val updateStateLoginUseCase: UpdateStateLoginUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state = _state

    init {
        getUser()
        updateStateLogin()
        getPromotion()
        getDrinkCategory()
        getDrink()
    }

    private fun updateStateLogin() {
        viewModelScope.launch {
            updateStateLoginUseCase()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getPhoneUseCase().collect {
                if (it != "") {
                    getUserUseCase(it).collect {
                        if (it is Result.Success) {
                            _state.value = _state.value.copy(_user = it.data)
                        }
                    }
                }
            }
        }
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
        viewModelScope.launch {
            getPhoneUseCase().collect {
                if (it != "") {
                    val itemsVoucher =
                        getPromotionUseCase(it).cachedIn(viewModelScope)
                    _state.value = _state.value.copy(_itemsVoucher = itemsVoucher)
                    itemsVoucher.collect {
                        VoucherHolder.setVoucher(it)
                    }
                }
            }
        }
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
                val currentPromotionIndex =
                    (_state.value.currentPromotionIndex + 1) % event.numberVouchers
                _state.value =
                    _state.value.copy(_currentPromotionIndex = currentPromotionIndex)
            }
        }
    }

}

