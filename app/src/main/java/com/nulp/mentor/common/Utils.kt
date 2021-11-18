package com.nulp.mentor.common

import okhttp3.internal.and
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun sha1Hash(toHash: String): String {
    var hash: String? = null
    try {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-1")
        var bytes = toHash.toByteArray(charset("UTF-8"))
        digest.update(bytes, 0, bytes.size)
        bytes = digest.digest()

        // This is ~55x faster than looping and String.formating()
        hash = bytesToHex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }
    return hash?:""
}

fun bytesToHex(bytes: ByteArray): String? {
    val hexArray = "0123456789ABCDEF".toCharArray()
    val hexChars = CharArray(bytes.size * 2)
    for (j in bytes.indices) {
        val v: Int = bytes[j] and 0xFF
        hexChars[j * 2] = hexArray[v ushr 4]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}