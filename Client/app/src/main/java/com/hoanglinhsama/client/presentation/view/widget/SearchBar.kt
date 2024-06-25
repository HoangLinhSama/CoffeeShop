package com.hoanglinhsama.client.presentation.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.hoanglinhsama.client.presentation.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.ui.theme.DarkCharcoal
import com.hoanglinhsama.client.presentation.ui.theme.ShadowBlack
import com.hoanglinhsama.client.presentation.ui.theme.SpanishGray

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onFilterClick: () -> Unit,
    onSearchClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    TextField(
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
            IconButton(onClick = onSearchClick) {
                Icon(
                    Icons.Filled.Search, contentDescription = null, tint = Color.White
                )
            }
        },
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = ShadowBlack,
                ambientColor = ShadowBlack,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = SpanishGray,
            unfocusedPlaceholderColor = SpanishGray,
            focusedContainerColor = DarkCharcoal,
            unfocusedContainerColor = DarkCharcoal,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = CopperRed
        ),
        trailingIcon = {
            Row(modifier = Modifier.padding(end = 4.dp)) {
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier.background(
                        color = CopperRed,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyBoardController?.hide()
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ClientTheme {
        SearchBar(Modifier, "", "Search drink", onFilterClick = {}, onSearchClick = {}) {

        }
    }
}