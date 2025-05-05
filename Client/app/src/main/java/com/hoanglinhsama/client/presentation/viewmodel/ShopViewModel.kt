package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.domain.usecase.main.GetShopUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.ShopEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.ShopState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val getShopUseCase: GetShopUseCase) : ViewModel() {
    private val _state = mutableStateOf(ShopState())
    val state = _state

    init {
        getShop()
    }

    private fun getShop() {
        val itemsShop = getShopUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsShop = itemsShop)
    }

    // TODO ("Deploy events on ShopScreen)
    fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.UpdateSearchShopEvent -> {
                _state.value = _state.value.copy(_searchShop = event.value)
            }

            is ShopEvent.OnFilterClickEvent -> {

            }

            is ShopEvent.OnSearchClickEvent -> {

            }
        }
    }
}