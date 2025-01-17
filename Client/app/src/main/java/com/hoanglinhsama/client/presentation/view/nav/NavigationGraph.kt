package com.hoanglinhsama.client.presentation.view.nav

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.hoanglinhsama.client.presentation.view.nav.Route.SplashScreen
import com.hoanglinhsama.client.presentation.view.screen.LoginScreen
import com.hoanglinhsama.client.presentation.view.screen.OnBoardingScreen
import com.hoanglinhsama.client.presentation.view.screen.SplashScreen
import com.hoanglinhsama.client.presentation.viewmodel.LoginViewModel
import com.hoanglinhsama.client.presentation.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.delay

@Composable
fun NavigationGraph(startDestination: String, activity: Activity) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = startDestination) {
        navigation(
            route = Route.SplashNavigation.route, startDestination = SplashScreen.route
        ) {
            composable(route = SplashScreen.route) {
                SplashScreen()
                LaunchedEffect(Unit) {
                    delay(2000L)
                    navigationController.navigate(Route.OnBoardingNavigation.route) {
                        popUpTo(Route.SplashNavigation.route) { inclusive = true }
                    }
                }
            }
        }
        navigation(
            route = Route.OnBoardingNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(onBoardingViewModel.state.value) {
                    navigationController.navigate(Route.AuthNavigation.route) {
                        popUpTo(Route.OnBoardingNavigation.route) { inclusive = true }
                    }
                }
            }
        }
        navigation(
            route = Route.AuthNavigation.route, startDestination = Route.LoginScreen.route
        ) {
            composable(route = Route.LoginScreen.route) {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    activity, loginViewModel.state.value, loginViewModel::onEvent, {
                        navigationController.navigate(Route.MainNavigation.route) {
                            popUpTo(Route.AuthNavigation.route){
                                inclusive = true
                            }
                        }
                    }
                ) {
                    navigationController.popBackStack()
                }
            }
            // TODO ("Deploy SignUpScreen and ForgetPasswordScreen with other authentication")
            composable(route = Route.SignUpScreen.route) {

            }
            composable(route = Route.ForgetPasswordScreen.route) {

            }
        }
        navigation(
            route = Route.MainNavigation.route, startDestination = Route.MainNavigatorScreen.route
        ) {
            composable(route = Route.MainNavigatorScreen.route) {
                MainNavigator()
            }
        }
    }
}