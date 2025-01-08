package com.hoanglinhsama.client.presentation.view.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.hoanglinhsama.client.presentation.view.screen.OnBoardingScreen
import com.hoanglinhsama.client.presentation.viewmodel.OnBoardingViewModel

@Composable
fun NavigationGraph(startDestination: String) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = startDestination) {
        navigation(
            route = Route.OnBoardingNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(onBoardingViewModel.state.value) {
                    onBoardingViewModel::onEvent
                }
            }
        }
        navigation(
            route = Route.MainNavigation.route,
            startDestination = Route.MainNavigatorScreen.route
        ) {
            composable(route = Route.MainNavigatorScreen.route) {
                MainNavigator()
            }
        }
    }
}