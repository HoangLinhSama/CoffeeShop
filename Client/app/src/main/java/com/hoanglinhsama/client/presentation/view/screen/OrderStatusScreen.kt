package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.viewmodel.event.OrderStatusEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.OrderStatusState
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

@Composable
fun OrderStatusScreen(
    orderId: Int,
    state: OrderStatusState,
    event: (OrderStatusEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowTitle, imageStatus) = createRefs()
        Row(
            modifier = Modifier.constrainAs(rowTitle) {
                top.linkTo(parent.top, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_home),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClick()
                    }
                    .size(24.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Trạng thái đơn hàng",
                color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderStatusScreenPreview() {
    ClientTheme(dynamicColor = false) {
        OrderStatusScreen(1, OrderStatusState(), {}) {

        }
    }
}