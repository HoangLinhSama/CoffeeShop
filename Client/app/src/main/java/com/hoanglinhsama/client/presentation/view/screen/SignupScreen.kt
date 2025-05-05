package com.hoanglinhsama.client.presentation.view.screen

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hoanglinhsama.client.R
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Policies
import com.hoanglinhsama.client.presentation.view.ui.anim.LoadingOverlay
import com.hoanglinhsama.client.presentation.view.ui.anim.shimmerEffect
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.view.ui.theme.CopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.Cultured
import com.hoanglinhsama.client.presentation.view.ui.theme.DarkCharcoal2
import com.hoanglinhsama.client.presentation.view.ui.theme.Dimens
import com.hoanglinhsama.client.presentation.view.ui.theme.GainsBoro
import com.hoanglinhsama.client.presentation.view.ui.theme.LightCopperRed
import com.hoanglinhsama.client.presentation.view.ui.theme.SpanishGray
import com.hoanglinhsama.client.presentation.view.util.HandleFlowResult
import com.hoanglinhsama.client.presentation.view.util.handlePagingResult
import com.hoanglinhsama.client.presentation.view.widget.PolicySheetShimmerEffect
import com.hoanglinhsama.client.presentation.viewmodel.event.SignupEvent
import com.hoanglinhsama.client.presentation.viewmodel.state.SignupState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    phone: String,
    state: SignupState,
    event: (SignupEvent) -> Unit,
    activityResultLauncher: ActivityResultLauncher<Intent>?,
    requestPermissionLauncher: ActivityResultLauncher<String>?,
    onBackClick: () -> Unit,
    onSignupSuccess: () -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val itemsPolicy = state.itemsPolicy?.collectAsLazyPagingItems()
    BottomSheetScaffold(
        modifier = Modifier.imePadding(),
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.White,
        sheetContent = {
            Column(
                modifier = Modifier.padding(
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
                                        style = MaterialTheme.typography.labelMedium,
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
        },
        sheetPeekHeight = 0.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Cultured)
        ) {
            LaunchedEffect(key1 = state.signupResultFlow) {
                state.signupResultFlow?.collect {
                    event(SignupEvent.UpdateResultSignup(it))
                    if (it is Result.Success) {
                        event(SignupEvent.SavePhoneEvent(phone) {
                            onSignupSuccess()
                        })
                    }
                }
            }
            state.resultSignup?.let {
                HandleFlowResult(it) {
                    LoadingOverlay()
                }
            }
            val (imageAvatar, rowPolicy, buttonSignup, rowTitle, textFieldLastName, textFieldFirstName, textFieldAddress) = createRefs()
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
                    text = "Đăng ký",
                    color = DarkCharcoal2,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = Dimens.sizeTitle)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(imageAvatar) {
                        top.linkTo(rowTitle.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                    }
                    .clickable {
                        event(
                            SignupEvent.CheckPermissionEvent(
                                activityResultLauncher!!, requestPermissionLauncher!!
                            )
                        )
                    }, color = GainsBoro
            ) {
                if (state.uriAvatar != "") {
                    AsyncImage(
                        model = state.uriAvatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_camera),
                            contentDescription = null,
                            tint = SpanishGray
                        )
                    }
                }
            }
            val focusManager = LocalFocusManager.current
            TextField(
                value = state.firstName,
                onValueChange = {
                    event(SignupEvent.UpdateFirstNameEvent(it))
                },
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(textFieldFirstName) {
                        top.linkTo(imageAvatar.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .border(
                        width = 1.dp,
                        color = if (state.listTextFieldFocus[0]) CopperRed else GainsBoro,
                        shape = RoundedCornerShape(Dimens.roundedCornerSize)
                    )
                    .onFocusChanged {
                        event(SignupEvent.FocusChangeEvent(0, it.isFocused))
                    },
                textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                placeholder = {
                    Text(
                        text = "Tên"
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
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            TextField(
                value = state.lastName,
                onValueChange = {
                    event(SignupEvent.UpdateLastNameEvent(it))
                },
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(textFieldLastName) {
                        top.linkTo(textFieldFirstName.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .border(
                        width = 1.dp,
                        color = if (state.listTextFieldFocus[1]) CopperRed else GainsBoro,
                        shape = RoundedCornerShape(Dimens.roundedCornerSize)
                    )
                    .onFocusChanged {
                        event(SignupEvent.FocusChangeEvent(1, it.isFocused))
                    },
                textStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                placeholder = {
                    Text(
                        text = "Họ"
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
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            TextField(
                value = state.address,
                onValueChange = {
                    event(SignupEvent.UpdateAddressEvent(it))
                },
                maxLines = 2,
                modifier = Modifier
                    .constrainAs(textFieldAddress) {
                        top.linkTo(textFieldLastName.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .clip(RoundedCornerShape(Dimens.roundedCornerSize))
                    .border(
                        width = 1.dp,
                        color = if (state.listTextFieldFocus[2]) CopperRed else GainsBoro,
                        shape = RoundedCornerShape(Dimens.roundedCornerSize)
                    )
                    .onFocusChanged {
                        event(SignupEvent.FocusChangeEvent(2, it.isFocused))
                    },
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
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Row(
                modifier = Modifier.constrainAs(rowPolicy) {
                    top.linkTo(textFieldAddress.bottom, Dimens.mediumMargin)
                    start.linkTo(parent.start, Dimens.mediumMargin)
                    end.linkTo(parent.end, Dimens.mediumMargin)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isChecked, onCheckedChange = {
                        event(SignupEvent.CheckBoxCheckEvent(it))
                    }, colors = CheckboxDefaults.colors(
                        checkedColor = CopperRed, uncheckedColor = SpanishGray,
                    ), enabled = state.isReadPolicy
                )
                Column {
                    Text(
                        text = "Tôi đồng ý với các ",
                        color = DarkCharcoal2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "Điều khoản và điều kiện của Coffee Shop",
                        color = CopperRed,
                        style = MaterialTheme.typography.labelMedium.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                            event(SignupEvent.ReadPolicyEvent)
                        })
                }
            }
            Button(
                modifier = Modifier
                    .constrainAs(buttonSignup) {
                        bottom.linkTo(parent.bottom, Dimens.mediumMargin)
                        start.linkTo(parent.start, Dimens.mediumMargin)
                        end.linkTo(parent.end, Dimens.mediumMargin)
                        width = Dimension.fillToConstraints
                    }
                    .height(Dimens.buttonHeight),
                shape = RoundedCornerShape(Dimens.roundedCornerSize),
                onClick = {
                    if (state.firstName.isNotEmpty() && state.lastName.isNotEmpty() && state.address.isNotEmpty() && state.isChecked && state.isReadPolicy) event(
                        SignupEvent.ButtonSignupClickEvent(phone)
                    )
                    else Toast.makeText(
                        context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CopperRed
                )
            ) {
                Text(
                    text = "Đăng ký",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    val listPolicy = listOf(
        Policies(
            "1. Chính sách bảo mật",
            "Ứng dụng thu thập thông tin như họ tên, số điện thoại, email, địa chỉ khi khách hàng đăng ký tài khoản hoặc đặt hàng.\n" + "Các thông tin này chỉ được sử dụng để phục vụ nhu cầu mua hàng, giao hàng, chăm sóc khách hàng và cải thiện dịch vụ.\n" + "Dữ liệu cá nhân của khách hàng được mã hóa và lưu trữ an toàn.\n" + "Ứng dụng không chia sẻ thông tin người dùng với bên thứ ba mà không có sự đồng ý của họ."
        )
    )
    val mockPolicyPagingData = PagingData.from(listPolicy)
    ClientTheme(dynamicColor = false) {
        SignupScreen(
            "", SignupState(_itemsPolicy = flowOf(mockPolicyPagingData)), {}, null, null, {}
        ) {

        }
    }
}