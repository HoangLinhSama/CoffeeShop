package com.hoanglinhsama.client.presentation.view.nav

sealed class Route(var route: String) {
    object SplashScreen : Route("splashScreen")
    object MainNavigation : Route("mainNavigation")
    object MainNavigatorScreen : Route("mainNavigatorScreen")
    object HomeScreen : Route("homeScreen")
    object ShopScreen : Route("shopScreen")
    object PromotionScreen : Route("promotionScreen")
    object OtherScreen : Route("otherScreen")
    object DetailDrinkScreen : Route("detailDrinkScreen")
    object OrderScreen : Route("orderScreen")
    object OnBoardingNavigation : Route("onBoardingNavigation")
    object OnBoardingScreen : Route("onBoardingScreen")
}