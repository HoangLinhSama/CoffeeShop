package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.presentation.ui.theme.ChineseBlack
import com.hoanglinhsama.client.presentation.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.ui.theme.DarkCharcoal
import com.hoanglinhsama.client.presentation.view.widget.SearchBar

@Composable
fun HomeScreen(
    user: User?,
    onNotificationClick: () -> Unit,
    onMessageClick: () -> Unit,
    onDownArrowClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (constraintLayout1, constraintLayout2) = createRefs()
        ConstraintLayout(modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(ChineseBlack, DarkCharcoal),
                    start = Offset.Zero,
                    end = Offset(0F, 500F),
                )
            )
            .height(280.dp)
            .constrainAs(constraintLayout1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
        ) {
            val (row1, textFieldSearch) = createRefs()
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .constrainAs(row1) {
                        start.linkTo(parent.start, 30.dp)
                        end.linkTo(parent.end, 30.dp)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hi, " + user?.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                )
                IconButton(
                    onClick = onDownArrowClick
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onNotificationClick
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = onMessageClick
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_message),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            SearchBar(
                modifier = Modifier
                    .constrainAs(textFieldSearch) {
                        top.linkTo(row1.bottom, 28.dp)
                        start.linkTo(parent.start, 30.dp)
                        end.linkTo(parent.end, 30.dp)
                        width = Dimension.fillToConstraints
                    }
                    .wrapContentHeight(),
                value = "",
                placeholder = "Search drink",
                onFilterClick = { /*TODO*/ },
                onSearchClick = { /*TODO*/ }) {

            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Cultured)
                .constrainAs(constraintLayout2) {
                    top.linkTo(constraintLayout1.bottom)
                    start.linkTo(parent.start)
                }
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClientTheme {
        HomeScreen(null, {}, {}, {})
    }
}