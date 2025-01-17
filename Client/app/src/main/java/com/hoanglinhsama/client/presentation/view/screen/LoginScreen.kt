package com.hoanglinhsama.client.presentation.view.screen

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.hoanglinhsama.client.presentation.view.widget.OtpCard
import com.hoanglinhsama.client.presentation.viewmodel.event.LoginEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.LoginState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    activity: Activity? = null,
    state: LoginState,
    event: (LoginEvent) -> Unit,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    BottomSheetScaffold(
        modifier = Modifier.imePadding(),
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.White,
        sheetContent = {
            OtpCard(
                Modifier,
                context,
                state.shouldStartCountdown,
                6,
                state.phoneNumber,
                state.timeOtpRemaining,
                state.listCharacterOtp, {
                    event(
                        LoginEvent.ResendingOtpEvent(
                            activity!!,
                            formatPhoneNumberToE164(
                                state.phoneNumber, "+84"
                            ),
                            token = state.tokenResend!!
                        ) { status, message, token ->
                            if (!status) {
                                Toast.makeText(
                                    context,
                                    "Có lỗi xảy ra trong quá trình xác thực, vui lòng kiểm tra lại",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (message != null) {
                                event(LoginEvent.MessageSendVerCodeEvent(message))
                                event(LoginEvent.TokenResendEvent(token))
                            } else {
                                Toast.makeText(
                                    context,
                                    "Quá trình xác thực sẽ tự động hoàn tất",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                }, {
                    event(LoginEvent.TimeOtpRemainingEvent(it))
                },
                {
                    event(
                        LoginEvent.VerifyCodeEvent(
                            state.messageSendVerCode,
                            it
                        ) { status, message, token ->
                            if (!status) {
                                Toast.makeText(
                                    context,
                                    "Mã xác thực không đúng, vui lòng kiểm tra lại",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                onLoginSuccess()
                            }
                        })
                }) { index, newValue ->
                event(LoginEvent.OtpEnterEvent(index, newValue))
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Cultured)
        ) {
            val (rowTitle, rowRemember, buttonLogin, textFieldPhoneNumber, rowDivide, columnMethodLogin) = createRefs()
            Row(
                modifier = Modifier.constrainAs(rowTitle) {
                    top.linkTo(parent.top, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackClick()
                    })
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Đăng nhập",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
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
                        shape = RoundedCornerShape(Dimens.roundedCornerSize), onClick = {
                            event(LoginEvent.LoginMethodClickEvent(it))
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = state.listMethodLogin[it].containerColor,
                            contentColor = state.listMethodLogin[it].contentColor
                        ), modifier = Modifier
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
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .padding(0.dp)
                        .height(1.dp)
                        .weight(1f)
                        .background(GainsBoro)
                )
                Text(
                    text = "hoặc",
                    style = MaterialTheme.typography.labelMedium,
                    color = SpanishGray,
                    modifier = Modifier.padding(
                        start = Dimens.smallMargin, end = Dimens.smallMargin
                    )
                )
                Box(
                    Modifier
                        .padding(0.dp)
                        .weight(1f)
                        .height(1.dp)
                        .background(GainsBoro)
                )
            }
            val focusManager = LocalFocusManager.current
            TextField(value = state.phoneNumber,
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
                        color = if (state.isFocus) CopperRed else GainsBoro,
                        shape = RoundedCornerShape(Dimens.roundedCornerSize)
                    )
                    .onFocusChanged {
                        event(LoginEvent.TextFieldFocusEvent(it.isFocused))
                    },
                textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                placeholder = {
                    Text(
                        text = "Số điện thoại", style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        )
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
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Row(
                modifier = Modifier.constrainAs(rowRemember) {
                    top.linkTo(textFieldPhoneNumber.bottom, Dimens.smallMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isCheckRemember, onCheckedChange = {
                        event(LoginEvent.RememberPhoneEvent(it))
                    }, colors = CheckboxDefaults.colors(
                        checkedColor = CopperRed, uncheckedColor = SpanishGray,
                    )
                )
                Text(
                    text = "Ghi nhớ tôi",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
            }
            Button(modifier = Modifier
                .constrainAs(buttonLogin) {
                    bottom.linkTo(parent.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }
                .height(Dimens.buttonHeight),
                shape = RoundedCornerShape(Dimens.roundedCornerSize),
                onClick = {
                    if (state.phoneNumber.length == 10) {
                        event(LoginEvent.ButtonLoginClickEvent(
                            activity!!,
                            formatPhoneNumberToE164(
                                state.phoneNumber, "+84"
                            )
                        ) { status, message, token ->
                            if (!status) {
                                Toast.makeText(
                                    context,
                                    "Có lỗi xảy ra trong quá trình xác thực, vui lòng kiểm tra lại",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (message != null) {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                    event(LoginEvent.ShouldStartCountdownEvent(true))
                                }
                                event(LoginEvent.MessageSendVerCodeEvent(message))
                                event(LoginEvent.TokenResendEvent(token))
                            } else {
                                Toast.makeText(
                                    context,
                                    "Quá trình xác thực sẽ tự động hoàn tất",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.phoneNumber.length == 10) CopperRed else GainsBoro
                )
            ) {
                Text(
                    text = "Đăng nhập",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }

}

fun formatPhoneNumberToE164(phoneNumber: String, countryCode: String): String {
    return countryCode + phoneNumber.drop(1)
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
        LoginScreen(null, LoginState(listMethodLogin), {}, {}) {}
    }
}