package com.hoanglinhsama.client.presentation.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hoanglinhsama.client.data.source.remote.zalopay.config.AppInfo
import com.hoanglinhsama.client.presentation.view.nav.NavigationGraph
import com.hoanglinhsama.client.presentation.view.ui.theme.ClientTheme
import com.hoanglinhsama.client.presentation.viewmodel.MainViewModel
import com.hoanglinhsama.client.presentation.viewmodel.SignupViewModel
import com.hoanglinhsama.client.presentation.viewmodel.event.SignupEvent
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val signupViewModel by viewModels<SignupViewModel>()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { mainViewModel.state.value.splashCondition })
        }
        setRegisterForActivityResult()
        setContent {
            ClientTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(
                        startDestination = mainViewModel.state.value.startDestination,
                        this,
                        activityResultLauncher,
                        requestPermissionLauncher,
                        signupViewModel
                    )
                }
            }
        }
    }

    private fun setRegisterForActivityResult() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                signupViewModel.onEvent(SignupEvent.HandleImageResultEvent(it, this) {
                    signupViewModel.onEvent(SignupEvent.UploadAvatarEvent(it))
                })
            }
        }
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    activityResultLauncher.launch(mainViewModel.intent)
                } else {
                    Toast.makeText(this, "Quyền truy cập bị từ chối", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}
