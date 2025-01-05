package com.hoanglinhsama.client.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hoanglinhsama.client.presentation.view.nav.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _startDestination = mutableStateOf(Route.MainNavigation.route)
    val startDestination: State<String> = _startDestination

    /* TODO: Set the screen that will appear when starting app */
}
