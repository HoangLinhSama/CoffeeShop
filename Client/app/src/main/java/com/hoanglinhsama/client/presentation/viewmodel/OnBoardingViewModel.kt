package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hoanglinhsama.client.domain.usecase.onboarding.GetOnboardingUseCase
import com.hoanglinhsama.client.domain.usecase.onboarding.UpdateStateEnterAppUseCase
import com.hoanglinhsama.client.presentation.viewmodel.event.OnBoardingEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val updateStateEnterAppUseCase: UpdateStateEnterAppUseCase,
    private val getOnboardingUseCase: GetOnboardingUseCase,
) :
    ViewModel() {
    private val _state = mutableStateOf(OnBoardingState())
    val state = _state

    init {
        getOnboarding()
    }

    private fun getOnboarding() {
        val itemsOnboarding = getOnboardingUseCase().cachedIn(viewModelScope)
        _state.value = _state.value.copy(_itemsOnboarding = itemsOnboarding)
    }

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.UpdateStateEnterAppEvent -> {
                viewModelScope.launch {
                    updateStateEnterAppUseCase()
                }
            }
        }
    }
}