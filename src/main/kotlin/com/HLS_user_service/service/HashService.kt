package com.HLS_user_service.service

import org.apache.commons.codec.binary.Base64
import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Service
class HashService(private val secureRandom: SecureRandom ) {

    companion object {
        private const val ITERATIONS = 210_000
        private const val KEY_LENGTH = 256
    }

    fun hashString(original: String, salt: String): String {
        require(original.isNotBlank()) { "Password cannot be blank" }
        val hash = hashStringIntoBytes(original, salt)
        return Base64.encodeBase64String(hash)
    }

    fun validateHash(password: String, salt: String, expectedHash: String): Boolean {
        val computed = hashStringIntoBytes(password, salt)
        val expected = Base64.decodeBase64(expectedHash)
        return computed.contentEquals(expected)
    }

    fun generateSalt(): String {
        val bytes = ByteArray(16)
        secureRandom.nextBytes(bytes)
        return Base64.encodeBase64String(bytes)
    }

    private fun hashStringIntoBytes(password: String, salt: String): ByteArray? {
        try {
            val spec = PBEKeySpec(
                password.toCharArray(),
                Base64.decodeBase64(salt),
                ITERATIONS,
                KEY_LENGTH
            )
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val key = skf.generateSecret(spec)
            spec.clearPassword()
            return key.encoded
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        }
    }
}
