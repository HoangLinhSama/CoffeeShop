package com.hoanglinhsama.client.presentation.viewmodel.state

data class DetailDrinkState(
    private val _isExpanded: Boolean = false,
    private val _indexSizeSelected: Int = 0,
    private val _isFavorite: Boolean = false,
    private val _listToppingChecked: List<Boolean> = List(9) { false },
    private val _countDrink: Int = 1,
    private val _noteOrder: String = " ",
    private val _isFocus: Boolean = false,
) {
    val isExpanded = _isExpanded
    val indexSizeSelected = _indexSizeSelected
    val isFavorite = _isFavorite
    val listToppingChecked = _listToppingChecked
    val countDrink = _countDrink
    val noteOrder = _noteOrder
    val isFocus = _isFocus
}