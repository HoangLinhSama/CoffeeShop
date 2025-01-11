package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.domain.model.LoginMethod
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.LightAzure
import com.hoanglinhsama.client.presentation.view.ui.theme.LightCopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.ShadowBlack
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.ui.theme.TrueBlue
import com.hoanglinhsama.client.presentation.viewmodel.event.LoginEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    event: (LoginEvent) -> Unit,
    onSignup: () -> Unit,
    onForgetPassword: () -> Unit,
    onBackClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Cultured)
    ) {
        val (rowTitle, rowRemember, buttonLogin, rowSignUp, rowForgetPassword, textFieldPhoneNumber, textFieldPassword, rowDivide, columnMethodLogin) = createRefs()
        Row(
            modifier = Modifier.constrainAs(rowTitle) {
                top.linkTo(parent.top, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onBackClick()
                })
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Đăng nhập", color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(modifier = Modifier.constrainAs(columnMethodLogin) {
            top.linkTo(rowTitle.bottom, Dimens.mediumMargin)
            start.linkTo(parent.start, Dimens.mediumMargin)
            end.linkTo(parent.end, Dimens.mediumMargin)
            width = Dimension.fillToConstraints
        }) {
            repeat(state.listMethodLogin!!.size) {
                Button(
                    shape = RoundedCornerShape(Dimens.roundedCornerSize),
                    onClick = {
                        event(LoginEvent.LoginMethodClickEvent(it))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = state.listMethodLogin[it].containerColor,
                        contentColor = state.listMethodLogin[it].contentColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            spotColor = ShadowBlack,
                            ambientColor = ShadowBlack,
                            shape = RoundedCornerShape(Dimens.roundedCornerSize)
                        )
                ) {
                    Icon(
                        painterResource(state.listMethodLogin[it].icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = Dimens.smallMargin),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "Đăng nhập bằng ${state.listMethodLogin[it].title}",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                if (it < state.listMethodLogin.size - 1) {
                    Spacer(modifier = Modifier.size(Dimens.smallMargin))
                }
            }
        }
        Row(
            modifier = Modifier.constrainAs(rowDivide) {
                top.linkTo(columnMethodLogin.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .padding(0.dp)
                    .height(1.dp)
                    .weight(1f)
                    .background(GainsBoro)
            )
            Text(
                text = "hoặc", style = MaterialTheme.typography.labelMedium, color = SpanishGray,
                modifier = Modifier.padding(start = Dimens.smallMargin, end = Dimens.smallMargin)
            )
            Box(
                Modifier
                    .padding(0.dp)
                    .weight(1f)
                    .height(1.dp)
                    .background(GainsBoro)
            )
        }
        val keyBoardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        TextField(
            value = state.phoneNumber,
            onValueChange = {
                event(LoginEvent.PhoneNumberEvent(it))
            },
            modifier = Modifier
                .constrainAs(textFieldPhoneNumber) {
                    top.linkTo(rowDivide.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .border(
                    width = 1.dp,
                    color = if (state.listIsFocus[0]) CopperRed else GainsBoro,
                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                )
                .onFocusChanged {
                    event(LoginEvent.TextFieldFocusEvent(0, it.isFocused))
                },
            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            placeholder = {
                Text(
                    text = "Số điện thoại",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            },
            leadingIcon = {
                Icon(Icons.Filled.Phone, contentDescription = null)
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
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )
        TextField(
            value = state.password,
            onValueChange = {
                event(LoginEvent.PasswordEvent(it))
            },
            trailingIcon = {
                IconButton(onClick = {
                    event(LoginEvent.ShowPasswordEvent(!state.isShowPassword))
                }) {
                    Icon(
                        painterResource(R.drawable.ic_eye_show),
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .constrainAs(textFieldPassword) {
                    top.linkTo(textFieldPhoneNumber.bottom, Dimens.smallMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .border(
                    width = 1.dp,
                    color = if (state.listIsFocus[1]) CopperRed else GainsBoro,
                    shape = RoundedCornerShape(Dimens.roundedCornerSize)
                )
                .onFocusEvent {
                    event(LoginEvent.TextFieldFocusEvent(1, it.isFocused))
                },
            textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            placeholder = {
                Text(
                    text = "Mật khẩu",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            },
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null)
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
                unfocusedLeadingIconColor = SpanishGray,
                focusedTrailingIconColor = CopperRed,
                unfocusedTrailingIconColor = SpanishGray
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (state.isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
        )
        Row(
            modifier = Modifier.constrainAs(rowRemember) {
                top.linkTo(textFieldPassword.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isCheckRemember,
                onCheckedChange = {
                    event(LoginEvent.RememberPasswordEvent(it))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CopperRed,
                    uncheckedColor = SpanishGray
                )
            )
            Text(
                text = "Nhớ mật khẩu",
                color = DarkCharcoal2,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
            )
        }
        Button(
            modifier = Modifier
                .constrainAs(buttonLogin) {
                    top.linkTo(rowRemember.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                .background(CopperRed)
                .height(Dimens.buttonHeight),
            onClick = {

            }
        ) {
            Text(
                text = "Đăng nhập",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
        Row(modifier = Modifier
            .constrainAs(rowForgetPassword) {
                bottom.linkTo(parent.bottom, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            }) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Quên mật khẩu ?", modifier = Modifier.clickable {
                    onForgetPassword()
                },
                style = MaterialTheme.typography.labelMedium,
                color = CopperRed
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.constrainAs(rowSignUp) {
                bottom.linkTo(rowForgetPassword.top, Dimens.mediumMargin)
                start.linkTo(parent.start, Dimens.mediumMargin)
                end.linkTo(parent.end, Dimens.mediumMargin)
                width = Dimension.fillToConstraints
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Chưa có tài khoản ?",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = DarkCharcoal2
            )
            Text(
                text = " Đăng ký ngay", style = MaterialTheme.typography.labelMedium,
                color = CopperRed,
                modifier = Modifier.clickable {
                    onSignup()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val listMethodLogin = listOf(
        LoginMethod(R.drawable.ic_email, "Email", Color.White, DarkCharcoal2),
        LoginMethod(R.drawable.ic_google, "Google", Color.White, DarkCharcoal2),
        LoginMethod(R.drawable.ic_facebook, "Facebook", TrueBlue, Color.White),
        LoginMethod(R.drawable.ic_twitter, "Twitter", LightAzure, Color.White)
    )
    ClientTheme(dynamicColor = false) {
        LoginScreen(LoginState(listMethodLogin), {}, {}, {}) {}
    }
}