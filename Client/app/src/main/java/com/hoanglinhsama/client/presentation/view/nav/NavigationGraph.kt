package com.hoanglinhsama.client.presentation.view.nav

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hoanglinhsama.client.presentation.view.nav.Route.SignUpScreen
import com.hoanglinhsama.client.presentation.view.screen.LoginScreen
import com.hoanglinhsama.client.presentation.view.screen.OnBoardingScreen
import com.hoanglinhsama.client.presentation.view.screen.SignupScreen
import com.hoanglinhsama.client.presentation.viewmodel.LoginViewModel
import com.hoanglinhsama.client.presentation.viewmodel.OnBoardingViewModel
import com.hoanglinhsama.client.presentation.viewmodel.SignupViewModel

@Composable
fun NavigationGraph(
    startDestination: String,
    activity: Activity,
    activityResultLauncher: ActivityResultLauncher<Intent>,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    viewModel: ViewModel,
) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = startDestination) {
        navigation(
            route = Route.OnBoardingNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(onBoardingViewModel.state.value, onBoardingViewModel::onEvent) {
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
                    activity,
                    loginViewModel.state.value,
                    loginViewModel::onEvent,
                    { phone, hadAccount ->
                        if (hadAccount) {
                            navigationController.navigate(Route.MainNavigation.route) {
                                popUpTo(Route.AuthNavigation.route) { inclusive = true }
                            }
                        } else {
                            navigationController.navigate(
                                "${SignUpScreen.route}/${phone}"
                            )
                        }
                    }
                ) {
                    navigationController.popBackStack()
                }
            }
            composable(
                route = "${SignUpScreen.route}/{phone}",
                arguments = listOf(navArgument("phone") {
                    type = NavType.StringType
                })
            ) {
                val phone = it.arguments?.getString("phone")
                phone?.let {
                    SignupScreen(
                        phone,
                        (viewModel as SignupViewModel).state.value,
                        viewModel::onEvent,
                        activityResultLauncher,
                        requestPermissionLauncher, { navigationController.popBackStack() }) {
                        navigationController.navigate(Route.MainNavigatorScreen.route) {
                            popUpTo(Route.AuthNavigation.route) { inclusive = true }
                        }
                    }
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