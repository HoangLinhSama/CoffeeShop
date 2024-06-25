package com.hoanglinhsama.client.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hoanglinhsama.client.presentation.ui.theme.ClientTheme

@Composable
fun TempScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color(0xFFFFFFFF),
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color(0xFFF8F8F8),
                )
                .padding(bottom = 112.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 88.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF131313),
                                        Color(0xFF303030),
                                    ),
                                    start = Offset.Zero,
                                    end = Offset(0F, 268F),
                                )
                            )
                            .padding(top = 17.dp, bottom = 100.dp, start = 28.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 29.dp, end = 17.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "09:41",
                                color = Color(0xFFDDDDDD),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .fillMaxWidth()
                            )
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(bottom = 24.dp, end = 30.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 6.dp, end = 8.dp)
                                    .width(140.dp)
                            ) {
                                Text(
                                    "Location",
                                    color = Color(0xFFB6B6B6),
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(bottom = 7.dp)
                                )
                                Text(
                                    "Bilzen, Tanjungbalai",
                                    color = Color(0xFFDDDDDD),
                                    fontSize = 14.sp,
                                )
                            }
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                            }
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(end = 30.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF303030),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .shadow(
                                    elevation = 4.dp,
                                    spotColor = Color(0x40000000),
                                )
                                .padding(
                                    top = 4.dp,
                                    bottom = 4.dp,
                                    start = 16.dp,
                                    end = 4.dp,
                                )
                        ) {
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                "Search coffee",
                                color = Color(0xFF979797),
                                fontSize = 14.sp,
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                            }
                            Column(
                                modifier = Modifier
                                    .width(44.dp)
                                    .background(
                                        color = Color(0xFFC67C4E),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 12.dp)
                            ) {
                                AsyncImage(
                                    model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .width(17.dp)
                                        .height(11.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .offset(x = 30.dp, y = 64.dp)
                        .align(Alignment.BottomStart)
                        .padding(start = 30.dp, bottom = 64.dp)
                        .width(315.dp)
                        .height(140.dp)
                        .background(
                            color = Color(0x66000000),
                        )
                        .padding(horizontal = 23.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 13.dp, bottom = 15.dp)
                            .width(60.dp)
                            .height(26.dp)
                            .background(
                                color = Color(0xFFEC5050),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(vertical = 7.dp)
                    ) {
                        Text(
                            "Promo",
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Buy one get one FREE",
                            color = Color(0xFFFFFFFF),
                            fontSize = 32.sp,
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .width(265.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .height(27.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF1C1C1C),
                                )
                        ) {
                        }
                        Column(
                            modifier = Modifier
                                .width(149.dp)
                                .height(23.dp)
                                .background(
                                    color = Color(0xFF1C1C1C),
                                )
                        ) {
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(121.dp)
                        .background(
                            color = Color(0xFFC67C4E),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 13.dp)
                ) {
                    Text(
                        "Cappuccino",
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(99.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 13.dp)
                ) {
                    Text(
                        "Machiato",
                        color = Color(0xFF2F4B4E),
                        fontSize = 14.sp,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(69.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 13.dp)
                ) {
                    Text(
                        "Latte",
                        color = Color(0xFF2F4B4E),
                        fontSize = 14.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .width(110.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(
                        "Americano",
                        color = Color(0xFF2F4B4E),
                        fontSize = 14.sp,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .width(149.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 14.dp)
                                .height(132.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .width(51.dp)
                                    .height(25.dp)
                                    .background(
                                        color = Color(0x26000000),
                                        shape = RoundedCornerShape(
                                            topStart = 16.dp,
                                            bottomStart = 16.dp,
                                        )
                                    )
                                    .padding(vertical = 7.dp)
                            ) {
                                AsyncImage(
                                    model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .width(17.dp)
                                        .height(11.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    "4.8",
                                    color = Color(0xFFFFFFFF),
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    }
                    Text(
                        "Cappucino",
                        color = Color(0xFF2F2D2C),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 9.dp)
                    )
                    Text(
                        "with Chocolate",
                        color = Color(0xFF9B9B9B),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            "$ 4.53",
                            color = Color(0xFF2F4B4E),
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .fillMaxWidth()
                        )
                        Column(
                            modifier = Modifier
                                .width(32.dp)
                                .background(
                                    color = Color(0xFFC67C4E),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp)
                        ) {
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .width(150.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(top = 4.dp, bottom = 4.dp, end = 12.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 14.dp, start = 4.dp)
                                .height(132.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .width(51.dp)
                                    .height(25.dp)
                                    .background(
                                        color = Color(0x26000000),
                                        shape = RoundedCornerShape(
                                            topStart = 16.dp,
                                            bottomStart = 16.dp,
                                        )
                                    )
                                    .padding(vertical = 7.dp)
                            ) {
                                AsyncImage(
                                    model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .width(17.dp)
                                        .height(11.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    "4.9",
                                    color = Color(0xFFFFFFFF),
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    }
                    Text(
                        "Cappucino",
                        color = Color(0xFF2F2D2C),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 9.dp, start = 12.dp)
                    )
                    Text(
                        "with Oat Milk",
                        color = Color(0xFF9B9B9B),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 12.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "$ 3.90",
                            color = Color(0xFF2F4B4E),
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .fillMaxWidth()
                        )
                        Column(
                            modifier = Modifier
                                .width(32.dp)
                                .background(
                                    color = Color(0xFFC67C4E),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp)
                        ) {
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 16.dp)
                            .width(149.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 4.dp)
                    ) {
                        Box {
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .height(132.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .width(51.dp)
                                        .height(25.dp)
                                        .background(
                                            color = Color(0x26000000),
                                            shape = RoundedCornerShape(
                                                topStart = 16.dp,
                                                bottomStart = 16.dp,
                                            )
                                        )
                                        .padding(vertical = 7.dp)
                                ) {
                                    AsyncImage(
                                        model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(end = 5.dp)
                                            .width(17.dp)
                                            .height(11.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        "4.5",
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 10.sp,
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .width(150.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 4.dp)
                    ) {
                        Box {
                            AsyncImage(
                                model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .width(17.dp)
                                    .height(11.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .height(132.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .width(51.dp)
                                        .height(25.dp)
                                        .background(
                                            color = Color(0x26000000),
                                            shape = RoundedCornerShape(
                                                topStart = 16.dp,
                                                bottomStart = 16.dp,
                                            )
                                        )
                                        .padding(vertical = 7.dp)
                                ) {
                                    AsyncImage(
                                        model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(end = 5.dp)
                                            .width(17.dp)
                                            .height(11.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        "4.0",
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 10.sp,
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .offset(x = 0.dp, y = 28.dp)
                        .align(Alignment.TopStart)
                        .padding(bottom = 28.dp)
                        .height(99.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .shadow(
                            elevation = 24.dp,
                            spotColor = Color(0x40E4E4E4),
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                top = 26.dp,
                                bottom = 7.dp,
                                start = 57.dp,
                                end = 57.dp,
                            )
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = "https://64.media.tumblr.com/8b78d4be0ee1d59afd5a0c0681abf326/ede3501d7255c7a2-03/s540x810/8c78ae0b43f3aef605dbb4160021a4c2c11a9cdf.png",
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .width(17.dp)
                                .height(11.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(bottom = 7.dp, start = 62.dp, end = 62.dp)
                            .width(10.dp)
                            .height(5.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFC67C4E),
                                        Color(0xFFECAA81),
                                    ),
                                    start = Offset.Zero,
                                    end = Offset(0F, 5F),
                                )
                            )
                    ) {
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .padding(horizontal = 120.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 19.dp)
                                .height(5.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0x4D000000),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        ) {
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TempScreenPreview() {
    ClientTheme {
        TempScreen()
    }
}