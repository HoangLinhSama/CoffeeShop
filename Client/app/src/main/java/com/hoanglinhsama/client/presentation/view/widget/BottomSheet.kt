package com.hoanglinhsama.client.presentation.view.widget

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.Policies
import com.hoanglinhsama.client.presentation.view.ui.anim.shimmerEffect
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.LightCopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Platinum
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import java.text.DecimalFormat

@Composable
fun BottomSheetOtp(
    modifier: Modifier,
    context: Context?,
    shouldStartCountdown: Boolean,
    sizeOTP: Int,
    phoneNumber: String,
    timeRemaining: Long,
    listCharacterOtp: List<String>,
    onResendingOtp: () -> Unit,
    onTimeRemaining: (Long) -> Unit,
    onAuthentication: (String) -> Unit,
    onValueChange: (Int, String) -> Unit,
) {
    val focusRequesters = remember { List(sizeOTP) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(
                top = Dimens.mediumMargin, start = Dimens.smallMargin, end = Dimens.smallMargin
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Xác nhận mã OTP",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle),
            color = DarkCharcoal2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin)
        )
        Text(
            text = "Một mã xác thực gồm $sizeOTP số đã được gửi đến Zalo hoặc số điện thoại $phoneNumber",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = DarkCharcoal2,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Nhập mã để tiếp tục",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = DarkCharcoal2,
            modifier = Modifier.padding(bottom = Dimens.smallMargin)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.mediumMargin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(sizeOTP) {
                TextField(
                    value = listCharacterOtp[it],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            onValueChange(it, newValue)
                            if (newValue.isNotEmpty() && it < sizeOTP - 1) {
                                focusRequesters[it + 1].requestFocus()
                            } else if (newValue.isEmpty() && it > 0) {
                                focusRequesters[it - 1].requestFocus()
                            }
                        }
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                        .aspectRatio(1f)
                        .border(
                            width = 1.dp,
                            color = if (!listCharacterOtp[it].isEmpty()) CopperRed else GainsBoro,
                            shape = RoundedCornerShape(Dimens.roundedCornerSize)
                        )
                        .focusable()
                        .focusRequester(focusRequesters[it]),
                    textStyle = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = DarkCharcoal2,
                        unfocusedTextColor = DarkCharcoal2,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CopperRed,
                        focusedContainerColor = LightCopperRed,
                        unfocusedContainerColor = GainsBoro
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (listCharacterOtp.all { it.isNotEmpty() }) {
                                focusManager.clearFocus()
                                onAuthentication(listCharacterOtp.joinToString(""))
                            }
                        })
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = Dimens.mediumMargin)
        ) {
            Text(
                text = "Bạn không nhận được mã,",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkCharcoal2,
            )
            if (shouldStartCountdown) {
                LaunchedEffect(key1 = timeRemaining) {
                    if (timeRemaining > 0) {
                        delay(1000L)
                        onTimeRemaining(timeRemaining - 1)
                    }
                }
            }
            Text(
                text = if (timeRemaining != 0L) " Gửi lại sau $timeRemaining s" else " Gửi lại",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = CopperRed,
                modifier = Modifier.clickable {
                    if (timeRemaining == 0L) {
                        onTimeRemaining(60L)
                        onResendingOtp()
                        Toast.makeText(context, "Mã OTP mới sẽ sớm được gửi", Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }
}

@Composable
fun BottomSheetPolicy(itemsPolicy: LazyPagingItems<Policies>?, modifier: Modifier) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(
                top = Dimens.mediumMargin,
                start = Dimens.mediumMargin,
                end = Dimens.mediumMargin,
                bottom = Dimens.mediumMargin
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsPolicy?.let {
            if (handlePagingResult(itemsPolicy, Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth(0.5f)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.size(Dimens.mediumMargin))
                    repeat(3) {
                        PolicySheetShimmerEffect(Modifier.fillMaxWidth())
                    }
                }) {
                Text(
                    text = "Điều khoản và điều kiện",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = Dimens.sizeTitle, fontWeight = FontWeight.Bold
                    ),
                    color = DarkCharcoal2
                )
                LazyColumn(
                    modifier = Modifier.padding(top = Dimens.mediumMargin),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(Dimens.mediumMargin)
                ) {
                    items(itemsPolicy.itemCount) {
                        Column {
                            Text(
                                text = itemsPolicy[it]?.title.toString(),
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                                color = DarkCharcoal2
                            )
                            Spacer(modifier = Modifier.size(Dimens.smallMargin))
                            Text(
                                text = itemsPolicy[it]?.content.toString(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                color = SpanishGray
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetUpdateInfoDelivery(
    modifier: Modifier,
    listInformation: List<String>,
    listTextFieldFocus: List<Boolean>,
    onValueChange: (Int, String) -> Unit,
    focusChangeEvent: (Int, Boolean) -> Unit,
    onConfirmInfo: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(Dimens.mediumMargin)
    ) {
        Text(
            text = "Thay đổi thông tin người nhận",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle),
            color = DarkCharcoal2,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextField(
            value = listInformation[0],
            onValueChange = {
                onValueChange(0, it)
            },
            maxLines = 1,
            modifier = Modifier
                .padding(top = Dimens.mediumMargin)
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .border(
                    width = 1.dp,
                    color = if (listTextFieldFocus[0]) CopperRed else GainsBoro,
                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                )
                .onFocusChanged {
                    focusChangeEvent(0, it.isFocused)
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            placeholder = {
                Text(
                    text = "Họ và tên"
                )
            },
            leadingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = DarkCharcoal2,
                unfocusedTextColor = DarkCharcoal2,
                focusedPlaceholderColor = SpanishGray,
                unfocusedPlaceholderColor = SpanishGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CopperRed,
                focusedContainerColor = LightCopperRed,
                unfocusedContainerColor = GainsBoro,
                focusedLeadingIconColor = CopperRed,
                unfocusedLeadingIconColor = SpanishGray
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        TextField(
            value = listInformation[1],
            onValueChange = {
                onValueChange(1, it)
            },
            maxLines = 1,
            modifier = Modifier
                .padding(top = Dimens.mediumMargin)
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .border(
                    width = 1.dp,
                    color = if (listTextFieldFocus[1]) CopperRed else GainsBoro,
                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                )
                .onFocusChanged {
                    focusChangeEvent(1, it.isFocused)
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            placeholder = {
                Text(
                    text = "Số điện thoại"
                )
            },
            leadingIcon = {
                Icon(Icons.Outlined.Phone, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = DarkCharcoal2,
                unfocusedTextColor = DarkCharcoal2,
                focusedPlaceholderColor = SpanishGray,
                unfocusedPlaceholderColor = SpanishGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CopperRed,
                focusedContainerColor = LightCopperRed,
                unfocusedContainerColor = GainsBoro,
                focusedLeadingIconColor = CopperRed,
                unfocusedLeadingIconColor = SpanishGray
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        TextField(
            value = listInformation[2],
            onValueChange = {
                onValueChange(2, it)
            },
            maxLines = 3,
            modifier = Modifier
                .padding(top = Dimens.mediumMargin)
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .border(
                    width = 1.dp,
                    color = if (listTextFieldFocus[2]) CopperRed else GainsBoro,
                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                )
                .onFocusChanged {
                    focusChangeEvent(2, it.isFocused)
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            placeholder = {
                Text(
                    text = "Địa chỉ"
                )
            },
            leadingIcon = {
                Icon(Icons.Outlined.Home, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = DarkCharcoal2,
                unfocusedTextColor = DarkCharcoal2,
                focusedPlaceholderColor = SpanishGray,
                unfocusedPlaceholderColor = SpanishGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CopperRed,
                focusedContainerColor = LightCopperRed,
                unfocusedContainerColor = GainsBoro,
                focusedLeadingIconColor = CopperRed,
                unfocusedLeadingIconColor = SpanishGray
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onConfirmInfo()
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = Dimens.mediumMargin)
                .fillMaxWidth()
                .height(Dimens.buttonHeight),
            colors = ButtonDefaults.buttonColors(
                containerColor = CopperRed
            )
        ) {
            Text(
                text = "Xong",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle)
            )
        }
    }
}

@Composable
fun BottomSheetUpdateTempOrder(
    drink: Drink,
    drinkOrder: DrinkOrder,
    modifier: Modifier = Modifier,
    isFocus: Boolean,
    focusChangeEvent: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onUpdateSize: (String) -> Unit,
    onUpdateTopping: (Int, Boolean) -> Unit,
    onUpdateDrinkCount: (Int) -> Unit,
    onConfirmInfo: () -> Unit,
) {
    val formatter = DecimalFormat("#,###")
    val focusManager = LocalFocusManager.current
    Box(modifier = modifier.background(Cultured)) {
        Column {
            Text(
                text = drink.name,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle),
                color = DarkCharcoal2,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = Dimens.mediumMargin,
                        start = Dimens.mediumMargin,
                        end = Dimens.mediumMargin
                    )
            )
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .padding(top = Dimens.mediumMargin)
                        .height(Dimens.smallMargin)
                        .background(GainsBoro)
                        .fillMaxWidth()
                )
                if (drink.priceSize!!.keys.toList()[0] != " ") {
                    Text(
                        text = "Size",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                        color = DarkCharcoal2,
                        modifier = Modifier
                            .padding(
                                top = Dimens.mediumMargin,
                                start = Dimens.mediumMargin,
                                end = Dimens.mediumMargin
                            )
                    )
                    Column(
                        modifier = Modifier.padding(
                            top = Dimens.smallMargin,
                            start = Dimens.mediumMargin,
                            end = Dimens.mediumMargin
                        )
                    ) {
                        repeat(drink.priceSize.size) { index ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = drink.priceSize.keys.toList()[index] == drinkOrder.size,
                                    onClick = {
                                        onUpdateSize(drink.priceSize.keys.toList()[index])
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = CopperRed,
                                        unselectedColor = SpanishGray,
                                    )
                                )
                                Text(
                                    text = drink.priceSize.keys.toList()[index],
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = DarkCharcoal2,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = drink.priceSize.values.toList()[index].let {
                                        formatter.format(
                                            it
                                        )
                                    } + "đ",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                    color = DarkCharcoal2
                                )
                            }
                            if (index != drink.priceSize.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .background(GainsBoro)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = Dimens.mediumMargin)
                            .height(Dimens.smallMargin)
                            .background(GainsBoro)
                            .fillMaxWidth()
                    )
                    if (drink.toppingPrice != null) {
                        Text(
                            text = "Topping",
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeSubTitle),
                            color = DarkCharcoal2,
                            modifier = Modifier
                                .padding(
                                    top = Dimens.mediumMargin,
                                    start = Dimens.mediumMargin,
                                    end = Dimens.mediumMargin
                                )
                        )
                        Column(
                            modifier = Modifier.padding(
                                top = Dimens.smallMargin,
                                start = Dimens.mediumMargin,
                                end = Dimens.mediumMargin
                            )
                        ) {
                            repeat(drink.toppingPrice.size) { index ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = drinkOrder.listTopping?.contains(drink.toppingPrice.keys.toList()[index]) == true,
                                        onCheckedChange = { isChecked ->
                                            onUpdateTopping(index, isChecked)
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = CopperRed,
                                            uncheckedColor = SpanishGray
                                        )
                                    )
                                    Text(
                                        text = drink.toppingPrice.keys.toList()[index],
                                        modifier = Modifier.weight(1f),
                                        color = DarkCharcoal2,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                    )
                                    Text(
                                        text = drink.toppingPrice.values.toList()[index].let {
                                            formatter.format(
                                                it
                                            )
                                        } + " đ",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                                        color = DarkCharcoal2
                                    )
                                }
                                if (index != drink.priceSize.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .background(GainsBoro)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = Dimens.mediumMargin)
                                .height(Dimens.smallMargin)
                                .background(GainsBoro)
                                .fillMaxWidth()
                        )
                        TextField(
                            value = drinkOrder.note,
                            onValueChange = {
                                onValueChange(it)
                            },
                            maxLines = 3,
                            modifier = Modifier
                                .padding(
                                    top = Dimens.mediumMargin,
                                    start = Dimens.mediumMargin,
                                    end = Dimens.mediumMargin,
                                    bottom = 150.dp
                                )
                                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                                .border(
                                    width = 1.dp,
                                    color = if (isFocus) CopperRed else GainsBoro,
                                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                                )
                                .onFocusChanged {
                                    focusChangeEvent(it.isFocused)
                                }
                                .fillMaxWidth(),
                            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                            placeholder = {
                                Text(
                                    text = "Thêm ghi chú"
                                )
                            },
                            leadingIcon = {
                                Icon(painterResource(R.drawable.ic_note), contentDescription = null)
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = DarkCharcoal2,
                                unfocusedTextColor = DarkCharcoal2,
                                focusedPlaceholderColor = SpanishGray,
                                unfocusedPlaceholderColor = SpanishGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = CopperRed,
                                focusedContainerColor = LightCopperRed,
                                unfocusedContainerColor = GainsBoro,
                                focusedLeadingIconColor = CopperRed,
                                unfocusedLeadingIconColor = SpanishGray
                            ), keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            })
                        )
                    }

                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = Dimens.mediumMargin,
                        topEnd = Dimens.mediumMargin
                    )
                )
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .shadow(
                    elevation = 24.dp,
                    spotColor = Platinum,
                    ambientColor = Platinum
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_minus),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = Dimens.mediumMargin)
                        .size(Dimens.mediumMargin)
                        .border(
                            width = 1.dp,
                            color = GainsBoro,
                            shape = RoundedCornerShape(3.dp)
                        )
                        .clickable {
                            if (drinkOrder.count > 1) {
                                onUpdateDrinkCount(drinkOrder.count - 1)
                            }
                        },
                    tint = if (drinkOrder.count == 1) GainsBoro else CopperRed
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = drinkOrder.count.toString(),
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimens.mediumMargin)
                        .border(
                            width = 1.dp,
                            color = GainsBoro,
                            shape = RoundedCornerShape(3.dp)
                        )
                        .clickable {
                            onUpdateDrinkCount(drinkOrder.count + 1)
                        },
                    tint = CopperRed
                )
            }
            Spacer(modifier = Modifier.padding(end = Dimens.mediumMargin))
            IconButton(
                onClick = {
                    onConfirmInfo()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .background(CopperRed)
                    .weight(0.5f)
                    .height(Dimens.buttonHeight),
            ) {
                Text(
                    text = drinkOrder.price.let { formatter.format(it) + " đ" },
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(end = Dimens.mediumMargin))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSheetUpdateTempOrderPreview() {
    ClientTheme(dynamicColor = false) {
        val priceSize = mapOf<String, Int>("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
        val toppingPrice = mapOf<String, Int>(
            "Shot Espresso" to 10000,
            "Trân châu trắng" to 10000,
            "Sốt Caramel" to 10000
        )
        val drink = Drink(
            1, "Bạc Xỉu", priceSize, "", 5F, "", toppingPrice, 1
        )
        val drinkOrder = DrinkOrder(
            1,
            "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Sốt Caramel"
            ), "", 2, 98000F
        )
        BottomSheetUpdateTempOrder(
            drink,
            drinkOrder,
            Modifier.fillMaxSize(),
            false,
            {},
            {},
            {},
            { index, isSelect -> }, {}) {}
    }
}


@Preview(showBackground = true)
@Composable
fun BottomSheetOtpPreview() {
    ClientTheme(dynamicColor = false) {
        BottomSheetOtp(
            Modifier.fillMaxWidth(),
            null,
            true,
            6,
            "0908334746",
            60,
            listOf("4", "9", "0", "3", "7", "6"),
            {},
            {},
            {}) { index, newValue -> }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetUpdateInfoDeliveryPreview() {
    ClientTheme(dynamicColor = false) {
        BottomSheetUpdateInfoDelivery(
            modifier = Modifier.fillMaxWidth(),
            listOf("", "", ""),
            listOf(false, false, false),
            { index, value -> },
            { index, isFocus -> }) {}
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetPolicyPreview() {
    ClientTheme(dynamicColor = false) {
        val listPolicy = listOf(
            Policies(
                "1. Chính sách bảo mật",
                "Ứng dụng thu thập thông tin như họ tên, số điện thoại, email, địa chỉ khi khách hàng đăng ký tài khoản hoặc đặt hàng.\n" + "Các thông tin này chỉ được sử dụng để phục vụ nhu cầu mua hàng, giao hàng, chăm sóc khách hàng và cải thiện dịch vụ.\n" + "Dữ liệu cá nhân của khách hàng được mã hóa và lưu trữ an toàn.\n" + "Ứng dụng không chia sẻ thông tin người dùng với bên thứ ba mà không có sự đồng ý của họ."
            )
        )
        val mockPolicyPagingData =
            flowOf(PagingData.from(listPolicy)).collectAsLazyPagingItems()
        BottomSheetPolicy(mockPolicyPagingData, Modifier.fillMaxWidth())
    }
}
