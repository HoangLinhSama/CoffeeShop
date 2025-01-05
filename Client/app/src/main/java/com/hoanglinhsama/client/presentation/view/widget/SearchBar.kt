package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal1
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.ShadowBlack
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    value: String,
    placeholder: String,
    isReadOnly: Boolean,
    onFilterClick: () -> Unit,
    onSearch: (String) -> Unit,
    onValueChange: (String) -> Unit,
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    TextField(
        readOnly = isReadOnly,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Normal
        ),
        placeholder = {
            Text(
                text = placeholder, style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        },
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(Dimens.smallIcon)
                )
            }
        },
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = ShadowBlack,
                ambientColor = ShadowBlack,
                shape = RoundedCornerShape(Dimens.roundedCornerSize)
            )
            .clickable {
                if (isReadOnly) onClick
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = SpanishGray,
            unfocusedPlaceholderColor = SpanishGray,
            focusedContainerColor = DarkCharcoal1,
            unfocusedContainerColor = DarkCharcoal1,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = CopperRed
        ),
        trailingIcon = {
            if (!isReadOnly) {
                Row(modifier = Modifier.padding(end = 4.dp)) {
                    IconButton(
                        onClick = onFilterClick,
                        modifier = Modifier.background(
                            color = CopperRed,
                            shape = RoundedCornerShape(Dimens.smallMargin)
                        )
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_filter),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(Dimens.smallIcon)
                        )
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyBoardController?.hide()
                onSearch
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ClientTheme(dynamicColor = false) {
        SearchBar(Modifier, {}, "", "Tìm đồ uống", false, {}, {}) {

        }
    }
}