package com.hoanglinhsama.client.presentation.nav

sealed class Route(var route: String) {
    object Home : Route("home")
    object Shop : Route("shop")
    object Promotion : Route("promotion")
    object Profile : Route("profile")
    object DetailItem : Route("detailItem")
}