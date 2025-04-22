package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Onboarding
import kotlinx.coroutines.flow.Flow

data class OnBoardingState(
    private val _itemsOnboarding: Flow<PagingData<Onboarding>>? = null,
) {
    val itemsOnboarding = _itemsOnboarding
}