package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.presentation.view.nav.Route

data class MainState(
    private val _startDestination: String = Route.OnBoardingNavigation.route,
    private val _splashCondition: Boolean = true,
) {
    val startDestination = _startDestination
    val splashCondition = _splashCondition
}