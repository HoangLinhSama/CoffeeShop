package com.hoanglinhsama.client.presentation.viewmodel

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoanglinhsama.client.domain.usecase.app.CheckSignedInUseCase
import com.hoanglinhsama.client.domain.usecase.app.CheckFirstTimeEnterAppUseCase
import com.hoanglinhsama.client.presentation.view.nav.Route
import com.hoanglinhsama.client.presentation.viewmodel.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val intent: Intent,
    private val checkFirstTimeEnterAppUseCase: CheckFirstTimeEnterAppUseCase,
    private val checkSignedInUseCase: CheckSignedInUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(MainState())
    val state = _state

    init {
        viewModelScope.launch {
            checkFirstTimeEnterAppUseCase().first().let { firstTimeEnterApp ->
                checkSignedInUseCase().collect { signedIn ->
                    if (firstTimeEnterApp) {
                        _state.value =
                            _state.value.copy(_startDestination = Route.OnBoardingNavigation.route)
                    } else if (!signedIn) {
                        _state.value =
                            _state.value.copy(_startDestination = Route.AuthNavigation.route)
                    } else {
                        _state.value =
                            _state.value.copy(_startDestination = Route.MainNavigation.route)
                    }
                    delay(100)
                    _state.value = _state.value.copy(_splashCondition = false)
                }
            }
        }
    }
}
