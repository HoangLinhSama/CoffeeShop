package com.hoanglinhsama.client.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.presentation.view.nav.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _startDestination = Route.AuthNavigation.route
    val startDestination = _startDestination

    /* TODO: Set the screen that will appear when starting app */
}
