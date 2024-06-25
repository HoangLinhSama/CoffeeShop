package com.hoanglinhsama.client.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(startDestination: String) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = startDestination) {

    }
}