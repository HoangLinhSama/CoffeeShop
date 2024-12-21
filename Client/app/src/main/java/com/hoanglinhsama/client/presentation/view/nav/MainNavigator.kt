package com.hoanglinhsama.client.presentation.view.nav

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.screen.HomeScreen
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(R.drawable.ic_home, "Home"),
            BottomNavigationItem(R.drawable.ic_shop, "Shop"),
            BottomNavigationItem(R.drawable.ic_order, "Order"),
            BottomNavigationItem(R.drawable.ic_promotion, "Promotion"),
            BottomNavigationItem(R.drawable.ic_profile, "Profile")
        )
    }

    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    var selectedItem by remember { mutableIntStateOf(0) }
    selectedItem = when (currentBackStackEntry?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.ShopScreen.route -> 1
        Route.OrderScreen.route -> 2
        Route.PromotionScreen.route -> 3
        Route.ProfileScreen.route -> 4
        else -> 0
    }

    // TODO: Hide the bottom navigation when the user is in the details screen

    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(size = 24.dp)),
                containerColor = Color.White
            ) {
                bottomNavigationItems.forEachIndexed { index, item ->
                    if (item.name == "Order") {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                when (index) {
                                    0 -> navigateToTab(navController, Route.HomeScreen.route)
                                    1 -> navigateToTab(navController, Route.ShopScreen.route)
                                    3 -> navigateToTab(navController, Route.PromotionScreen.route)
                                    4 -> navigateToTab(navController, Route.ProfileScreen.route)
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Box(
                                    modifier = Modifier
                                        .width(10.dp)
                                        .height(5.dp)
                                        .background(
                                            color = if (selectedItem == index) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = RoundedCornerShape(size = 18.dp)
                                        )

                                ) {

                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = colorResource(id = R.color.philippineGray),
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = CopperRed,
                modifier = Modifier
                    .offset(y = 50.dp)
                    .border(BorderStroke(5.dp, Color.White), CircleShape),
                onClick = { navigateToTab(navController, Route.OrderScreen.route) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_order), contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val itemsVoucher = homeViewModel.listPromotion.collectAsLazyPagingItems()
                val itemsDrinkCategory = homeViewModel.listDrinkCategory.collectAsLazyPagingItems()
                val itemsDrink = homeViewModel.listDrink.collectAsLazyPagingItems()
                Log.d("HLSM", "Size: ${itemsDrink.itemCount}")
                HomeScreen(
                    itemsVoucher = itemsVoucher,
                    itemsDrinkCategory = itemsDrinkCategory,
                    itemsDrink = itemsDrink,
                    onNotificationClick = {

                    },
                    onFavoriteClick = {

                    },
                    onFilterClick = {

                    },
                    onAvatarClick = {

                    },
                    onVoucherClick = {

                    },
                    onSearchDrink = {

                    }) {

                }
            }
        }
    }


}

fun navigateToTab(navController: NavHostController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

data class BottomNavigationItem(@DrawableRes val icon: Int, val name: String)

@Composable
@Preview
fun MainNavigatorPreview() {
    ClientTheme(dynamicColor = false) {
        MainNavigator()
    }
}

