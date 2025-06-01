package com.hoanglinhsama.client.data.source.remote.zalopay.helpers

import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HMacUtil {
    const val H_MAC_SHA_256 = "HmacSHA256"
    private fun hMacEncode(algorithm: String, key: String, data: String): ByteArray? {
        return try {
            val mac = Mac.getInstance(algorithm)
            val signingKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), algorithm)
            mac.init(signingKey)
            mac.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun hMacHexStringEncode(algorithm: String, key: String, data: String): String? {
        val hmacBytes = hMacEncode(algorithm, key, data) ?: return null
        return HexStringUtil.byteArrayToHexString(hmacBytes)
    }
}