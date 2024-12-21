package com.hoanglinhsama.client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.domain.usecase.GetDrinkCategoryUseCase
import com.hoanglinhsama.client.domain.usecase.GetDrinkUseCase
import com.hoanglinhsama.client.domain.usecase.GetPromotionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPromotionUseCase: GetPromotionUseCase,
    getDrinkCategoryUseCase: GetDrinkCategoryUseCase,
    getDrinkUseCase: GetDrinkUseCase,
) : ViewModel() {
    val listPromotion = getPromotionUseCase().cachedIn(viewModelScope)
    val listDrinkCategory = getDrinkCategoryUseCase().cachedIn(viewModelScope)
    val listDrink = getDrinkUseCase().cachedIn(viewModelScope)
}

