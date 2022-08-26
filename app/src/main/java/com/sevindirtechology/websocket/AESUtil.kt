package com.sevindirtechology.websocket

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object AESUtil {
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun encrypt(
        algorithm: String?,
        input: String,
        key: SecretKey?,
        iv: IvParameterSpec?
    ): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder()
            .encodeToString(cipherText)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun decrypt(
        algorithm: String?,
        cipherText: String?,
        key: SecretKey?,
        iv: IvParameterSpec?
    ): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(
            Base64.getDecoder()
                .decode(cipherText)
        )
        return String(plainText)
    }

    fun generateKey(n: Int): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(n)
        return keyGenerator.generateKey()
    }

    fun generateIv(): IvParameterSpec {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }
}