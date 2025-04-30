package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.DrinkOrder
import kotlinx.coroutines.flow.Flow

data class DetailDrinkState(
    private val _isExpanded: Boolean = false,
    private val _indexSizeSelected: Int = 0,
    private val _isFavorite: Boolean = false,
    private val _listToppingChecked: List<Boolean> = List(9) { false },
    private val _countDrink: Int = 1,
    private val _noteOrder: String = " ",
    private val _isFocus: Boolean = false,
    private val _createTempOrderResultFlow: Flow<Result<DrinkOrder>>? = null,
) {
    val isExpanded = _isExpanded
    val indexSizeSelected = _indexSizeSelected
    val isFavorite = _isFavorite
    val listToppingChecked = _listToppingChecked
    val countDrink = _countDrink
    val noteOrder = _noteOrder
    val isFocus = _isFocus
    val createTempOrderResultFlow = _createTempOrderResultFlow
}