package com.hoanglinhsama.client.presentation.view.nav

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.presentation.view.screen.DetailDrinkScreen
import com.hoanglinhsama.client.presentation.view.screen.HomeScreen
import com.hoanglinhsama.client.presentation.view.screen.OrderScreen
import com.hoanglinhsama.client.presentation.view.screen.OrderStatusScreen
import com.hoanglinhsama.client.presentation.view.screen.OtherScreen
import com.hoanglinhsama.client.presentation.view.screen.PromotionScreen
import com.hoanglinhsama.client.presentation.view.screen.SettingScreen
import com.hoanglinhsama.client.presentation.view.screen.ShopScreen
import com.hoanglinhsama.client.presentation.view.screen.VoucherScreen
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.viewmodel.DetailDrinkViewModel
import com.hoanglinhsama.client.presentation.viewmodel.HomeViewModel
import com.hoanglinhsama.client.presentation.viewmodel.OrderStatusViewModel
import com.hoanglinhsama.client.presentation.viewmodel.OrderViewModel
import com.hoanglinhsama.client.presentation.viewmodel.OtherViewModel
import com.hoanglinhsama.client.presentation.viewmodel.PromotionViewModel
import com.hoanglinhsama.client.presentation.viewmodel.SettingViewModel
import com.hoanglinhsama.client.presentation.viewmodel.ShopViewModel
import com.hoanglinhsama.client.presentation.viewmodel.VoucherViewModel
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderEvent

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigator(activity: Activity) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(R.drawable.ic_home, "Home"),
            BottomNavigationItem(R.drawable.ic_shop, "Shop"),
            BottomNavigationItem(R.drawable.ic_order, "Order"),
            BottomNavigationItem(R.drawable.ic_promotion, "Promotion"),
            BottomNavigationItem(R.drawable.ic_other, "Other")
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
        Route.OtherScreen.route -> 4
        else -> 0
    }
    val isBottomBarVisible = remember(key1 = currentBackStackEntry) {
        currentBackStackEntry?.destination?.route == Route.HomeScreen.route || (currentBackStackEntry?.destination?.route == Route.ShopScreen.route && !orderViewModel.state.value.shopScreenIsSelectMode) || currentBackStackEntry?.destination?.route == Route.PromotionScreen.route || currentBackStackEntry?.destination?.route == Route.OtherScreen.route
    }
    Scaffold(
        Modifier.fillMaxSize(), bottomBar = {
            if (isBottomBarVisible) {
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
                                selected = selectedItem == index, onClick = {
                                    when (index) {
                                        0 -> navigateToTab(navController, Route.HomeScreen.route)
                                        1 -> navigateToTab(navController, Route.ShopScreen.route)
                                        3 -> navigateToTab(
                                            navController, Route.PromotionScreen.route
                                        )

                                        4 -> navigateToTab(navController, Route.OtherScreen.route)
                                    }
                                }, icon = {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }, label = {
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
                                }, colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = colorResource(id = R.color.philippine_gray),
                                    indicatorColor = Color.Transparent
                                ), modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }, snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }, floatingActionButton = {
            if (isBottomBarVisible) {
                Box(
                    modifier = Modifier.offset(y = 50.dp)
                ) {
                    FloatingActionButton(
                        shape = CircleShape,
                        containerColor = CopperRed,
                        modifier = Modifier.border(BorderStroke(5.dp, Color.White), CircleShape),
                        onClick = { navigateToTab(navController, Route.OrderScreen.route) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_order),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    orderViewModel.state.value.listDrinkOrder?.let {
                        if (it.isNotEmpty()) {
                            val totalCount = it.sumOf { it.count }
                            val scale = remember { Animatable(1f) }
                            LaunchedEffect(totalCount) {
                                scale.animateTo(
                                    targetValue = 1.5f, animationSpec = tween(
                                        durationMillis = 100, easing = FastOutLinearInEasing
                                    )
                                )
                                scale.animateTo(
                                    targetValue = 1f, animationSpec = tween(durationMillis = 100)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .scale(scale.value)
                                    .size(Dimens.smallIcon)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = totalCount.toString(),
                                    color = CopperRed,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }
        }, floatingActionButtonPosition = FabPosition.Center
    ) {
        NavHost(
            navController = navController, startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    homeViewModel.state.value, homeViewModel::onEvent, onSearchClick = {

                    }) {
                    navController.navigate(
                        "${Route.DetailDrinkScreen.route}/${Uri.encode(Gson().toJson(it))}"
                    )
                }
            }
            composable(
                route = "${Route.DetailDrinkScreen.route}/{drink}",
                arguments = listOf(navArgument("drink") {
                    type = NavType.StringType
                })
            ) {
                val detailDrinkViewModel: DetailDrinkViewModel = hiltViewModel()
                val json = it.arguments?.getString("drink")
                val drink = Gson().fromJson(json, Drink::class.java)
                DetailDrinkScreen(
                    drink,
                    detailDrinkViewModel.state.value,
                    detailDrinkViewModel::onEvent,
                ) {
                    navController.popBackStack()
                }
            }
            composable(route = Route.OtherScreen.route) {
                val otherViewModel: OtherViewModel = hiltViewModel()
                OtherScreen(otherViewModel.state.value, otherViewModel::onEvent, {

                }, {

                }) {
                    when (it) {
                        2 -> {
                            navController.navigate(Route.SettingScreen.route)
                        }
                    }
                }
            }
            composable(route = Route.SettingScreen.route) {
                val settingViewModel: SettingViewModel = hiltViewModel()
                SettingScreen(settingViewModel.state.value, settingViewModel::onEvent, {}) {
                    navController.popBackStack()
                }
            }
            composable(route = Route.OrderScreen.route) {
                val resultShop =
                    navController.currentBackStackEntry?.savedStateHandle?.get<Shop>("shop")
                resultShop?.let {
                    orderViewModel.onEvent(OrderEvent.UpdateSelectShopEvent(it, false))
                }
                val resultVoucher =
                    navController.currentBackStackEntry?.savedStateHandle?.get<Voucher>("voucher")
                resultVoucher?.let {
                    orderViewModel.onEvent(OrderEvent.UpdateVoucherEvent(it))
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Voucher>("voucher")
                }
                val resultUseBean =
                    navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("useBean")
                resultUseBean?.let {
                    orderViewModel.onEvent(OrderEvent.UpdateUseBeanEvent(it))
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("useBean")
                }
                OrderScreen(
                    orderViewModel.eventFlow,
                    orderViewModel.state.value,
                    orderViewModel::onEvent,
                    {
                        navController.popBackStack()
                    },
                    {
                        navController.navigate(Route.ShopScreen.route)
                    },
                    { typeOrder, currentBean, subTotal, quantity, listDrinkCategory ->
                        val encodedListDrinkCategory =
                            listDrinkCategory.joinToString(separator = ",")
                        navController.navigate(
                            "${Route.VoucherScreen.route}/$typeOrder/$currentBean/$subTotal/$quantity/$encodedListDrinkCategory"
                        )
                    },
                    {
                        navController.popBackStack()
                    }) {
                    val openFromOrderHistory = false
                    navController.navigate(
                        "${Route.OrderStatusScreen.route}/$it/$openFromOrderHistory"
                    )
                }
            }
            composable(route = Route.ShopScreen.route) {
                val shopViewModel: ShopViewModel = hiltViewModel()
                ShopScreen(
                    orderViewModel.state.value.shopScreenIsSelectMode,
                    shopViewModel.state.value,
                    shopViewModel::onEvent,
                    {

                    }) { isSelectMode, shop ->
                    if (isSelectMode) {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "shop", shop
                        )
                        navController.popBackStack()
                    } else {
                        // TODO ("Deploy DetailShopScreen)
                    }
                }
            }
            composable(
                route = "${Route.VoucherScreen.route}/{typeOrder}/{currentBean}/{subTotal}/{quantity}/{listDrinkCategory}",
                arguments = listOf(navArgument("typeOrder") {
                    type = NavType.StringType
                }, navArgument("currentBean") {
                    type = NavType.IntType
                }, navArgument("subTotal") {
                    type = NavType.FloatType
                }, navArgument("quantity") {
                    type = NavType.IntType
                }, navArgument("listDrinkCategory") {
                    type = NavType.StringType
                })
            ) {
                val typeOrder = it.arguments?.getString("typeOrder")
                val currentBean = it.arguments?.getInt("currentBean")
                val subTotal = it.arguments?.getFloat("subTotal")
                val quantity = it.arguments?.getInt("quantity")
                val listDrinkCategory = it.arguments?.getString("listDrinkCategory")?.split(",")
                val voucherViewModel: VoucherViewModel = hiltViewModel()
                typeOrder?.let { typeOrder ->
                    currentBean?.let { currentBean ->
                        subTotal?.let { subTotal ->
                            quantity?.let { quantity ->
                                listDrinkCategory?.let { listDrinkCategory1 ->
                                    VoucherScreen(
                                        voucherViewModel.state.value,
                                        typeOrder,
                                        currentBean,
                                        subTotal,
                                        quantity,
                                        listDrinkCategory1,
                                        voucherViewModel::onEvent,
                                        {
                                            navController.popBackStack()
                                        },
                                        {
                                            navController.popBackStack()
                                        },
                                        {
                                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                                "voucher", it
                                            )
                                            navController.popBackStack()
                                        }) {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "useBean", it
                                        )
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            composable(route = Route.PromotionScreen.route) {
                val promotionViewmodel: PromotionViewModel = hiltViewModel()
                PromotionScreen(promotionViewmodel.state.value, {}) {

                }
            }
            composable(
                route = "${Route.OrderStatusScreen.route}/{orderId}/{openFromOrderHistory}",
                arguments = listOf(
                    navArgument("orderId") {
                        type = NavType.IntType
                    },
                    navArgument("openFromOrderHistory") {
                        type = NavType.BoolType
                    })

            ) {
                val orderId = it.arguments?.getInt("orderId")
                val openFromOrderHistory = it.arguments?.getBoolean("openFromOrderHistory")
                val orderStatusViewModel: OrderStatusViewModel = hiltViewModel()
                orderId?.let {
                    OrderStatusScreen(
                        activity,
                        it,
                        openFromOrderHistory == true,
                        orderStatusViewModel.state.value,
                        orderStatusViewModel::onEvent
                    ) {
                        navController.navigate(Route.HomeScreen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
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
