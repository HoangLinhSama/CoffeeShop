package com.hoanglinhsama.client.presentation.view.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.presentation.view.widget.ErrorCard
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun <T : Any> handlePagingResult(
    items: LazyPagingItems<T>,
    modifier: Modifier? = null,
    shimmerEffect: @Composable () -> Unit,
): Boolean {
    val context = LocalContext.current
    val loadState = items.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            shimmerEffect.invoke()
            false
        }

        error != null -> {
            if (modifier != null) {
                ErrorCard(error = error, modifier = modifier)
            } else {
                Toast.makeText(
                    context, when (error.error) {
                        is SocketTimeoutException -> {
                            "Server không phản hồi"
                        }

                        is ConnectException -> {
                            "Không có kết nối Internet"
                        }

                        is Exception -> {
                            when (error.error.message) {
                                "fail: no data found" -> "Không có dữ liệu"
                                else -> "Lỗi không xác định: ${error.error.message}"
                            }
                        }

                        else -> {
                            "Lỗi không xác định: ${error.error.message}"
                        }
                    }, Toast.LENGTH_LONG
                ).show()
            }
            false
        }

        else -> true
    }
}

@Composable
fun <T> HandleFlowResult(
    result: Result<T>,
    loading: @Composable () -> Unit,
) {
    val context = LocalContext.current
    when (result) {
        is Result.Loading -> {
            loading.invoke()
        }

        is Result.Success<T> -> {

        }

        is Result.Error -> {
            Toast.makeText(
                context,
                when (result.exception) {
                    is SocketTimeoutException -> "Sever không phản hồi"
                    is ConnectException -> "Không có kết nối Internet"
                    else -> "Lỗi không xác định: ${result.exception.message}"
                }, Toast.LENGTH_LONG
            ).show()
        }
    }
}
