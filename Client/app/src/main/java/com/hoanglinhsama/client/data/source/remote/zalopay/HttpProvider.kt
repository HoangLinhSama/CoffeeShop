package com.hoanglinhsama.client.data.source.remote.zalopay

import android.util.Log
import com.hoanglinhsama.client.presentation.di.ZaloPayOkHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class HttpProvider @Inject constructor(@ZaloPayOkHttpClient private val client: OkHttpClient) {
    suspend fun sendPost(
        url: String,
        formBody: RequestBody,
    ): JSONObject? = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                JSONObject(response.body?.string() ?: "")
            } else {
                Log.e("HttpProvider", "Request failed: ${response.body?.string()}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
