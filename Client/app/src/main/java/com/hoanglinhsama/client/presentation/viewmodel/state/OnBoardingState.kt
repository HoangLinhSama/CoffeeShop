package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.Page

data class OnBoardingState(
    private val _listPage: List<Page> = emptyList<Page>(),
) {
    val listPage = _listPage
}