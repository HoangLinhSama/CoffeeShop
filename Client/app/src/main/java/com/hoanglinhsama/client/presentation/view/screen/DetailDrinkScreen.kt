package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.presentation.view.nav.Route
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme

@Composable
fun DetailDrinkScreen(drink: Drink) {
    ConstraintLayout {
        
    }
}

@Preview
@Composable
fun Route.DetailDrinkScreenPreview() {
    ClientTheme(dynamicColor = false) {
        DetailDrinkScreen()
    }
}