package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro

@Composable
fun UseBeanDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(onDismissRequest = { }, title = {
        Text(
            text = title,
        )
    }, text = {
        Text(
            text = text,
        )
    }, confirmButton = {
        TextButton(onClick = {
            onConfirm()
        }) {
            Text("OK")
        }
    }, dismissButton = {
        TextButton(onClick = { onDismiss() }) {
            Text("Hủy")
        }
    })
}

@Composable
fun ConfirmInfoOrderDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = {
        onDismissRequest()
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .background(Color.White)
                .padding(
                    top = Dimens.mediumMargin,
                    start = Dimens.mediumMargin,
                    end = Dimens.mediumMargin
                )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                color = DarkCharcoal2
            )
            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkCharcoal2,
                modifier = Modifier.padding(
                    top = Dimens.smallMargin,
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.smallMargin)
                    .height(1.dp)
                    .background(GainsBoro)
            )
            Text(
                text = "Thay đổi thông tin",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = CopperRed,
                modifier = Modifier
                    .padding(
                        top = Dimens.smallMargin,
                    )
                    .clickable {
                        onDismissRequest()
                    }
            )
            Box(
                modifier = Modifier
                    .padding(top = Dimens.smallMargin)
                    .height(1.dp)
                    .background(GainsBoro)
                    .fillMaxWidth()
            )
            Text(
                text = "Xác nhận",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = CopperRed,
                modifier = Modifier
                    .padding(
                        top = Dimens.smallMargin,
                        bottom = Dimens.smallMargin
                    )
                    .clickable {
                        onConfirm()
                    }
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun UseBeanDialogPreview() {
    ClientTheme(dynamicColor = false) {
        UseBeanDialog("Đổi bean", "Bạn có muốn đổi 5 bean không ?", {}) {

        }
    }
}

@Preview(showBackground = false)
@Composable
fun ConfirmInfoOrderDialogPreview() {
    ClientTheme(dynamicColor = false) {
        ConfirmInfoOrderDialog(
            "Xác nhận thông tin đơn hàng",
            "Đơn hàng giao tận nơi sẽ được giao vào 9h30 thứ 5 20/03 tại Quận 12, Hồ Chí Minh",
            {}) {}
    }
}