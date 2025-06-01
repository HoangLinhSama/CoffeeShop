package com.hoanglinhsama.client.data.source.remote.zalopay.helpers

object HexStringUtil {
    private val HEX_CHAR_TABLE = byteArrayOf(
        '0'.code.toByte(), '1'.code.toByte(), '2'.code.toByte(), '3'.code.toByte(),
        '4'.code.toByte(), '5'.code.toByte(), '6'.code.toByte(), '7'.code.toByte(),
        '8'.code.toByte(), '9'.code.toByte(), 'a'.code.toByte(), 'b'.code.toByte(),
        'c'.code.toByte(), 'd'.code.toByte(), 'e'.code.toByte(), 'f'.code.toByte()
    )

    fun byteArrayToHexString(raw: ByteArray): String {
        val hex = ByteArray(raw.size * 2)
        var index = 0
        for (b in raw) {
            val v = b.toInt() and 0xFF
            hex[index++] = HEX_CHAR_TABLE[v ushr 4]
            hex[index++] = HEX_CHAR_TABLE[v and 0x0F]
        }
        return String(hex)
    }
}