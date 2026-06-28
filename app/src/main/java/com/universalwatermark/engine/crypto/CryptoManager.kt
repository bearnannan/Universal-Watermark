package com.universalwatermark.engine.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec

class CryptoManager {

    companion object {
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val ALIAS = "universal_watermark_ecdsa_key"
        private const val ALGORITHM = "SHA256withECDSA"
    }

    private val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
        load(null)
    }

    init {
        getOrCreateKeyPair()
    }

    private fun getOrCreateKeyPair() {
        if (!keyStore.containsAlias(ALIAS)) {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                KEYSTORE_PROVIDER
            )
            
            val parameterSpec = KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).run {
                setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                // Hardware backed if available
                // setUserAuthenticationRequired(false) // Default is false
                build()
            }
            
            keyPairGenerator.initialize(parameterSpec)
            keyPairGenerator.generateKeyPair()
        }
    }

    /**
     * Calculates SHA-256 hash of the given byte array and returns as Base64 string.
     */
    fun generateHash(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data)
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
    }

    /**
     * Signs the given text data using the ECDSA private key.
     * Output is Base64 encoded signature.
     */
    fun signData(data: String): String {
        val signature = Signature.getInstance(ALGORITHM)
        val entry = keyStore.getEntry(ALIAS, null) as? KeyStore.PrivateKeyEntry
            ?: throw IllegalStateException("KeyStore entry not found or invalid")
            
        signature.initSign(entry.privateKey)
        signature.update(data.toByteArray(Charsets.UTF_8))
        
        val signatureBytes = signature.sign()
        return Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
    }

    /**
     * Returns the Public Key as a Base64 string so it can be embedded in EXIF.
     */
    fun getPublicKeyBase64(): String {
        val entry = keyStore.getEntry(ALIAS, null) as? KeyStore.PrivateKeyEntry
            ?: throw IllegalStateException("KeyStore entry not found or invalid")
            
        val publicKey = entry.certificate.publicKey
        return Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
    }
    
    /**
     * Verifies the signature (useful for internal testing).
     */
    fun verifySignature(data: String, signatureBase64: String): Boolean {
        val signature = Signature.getInstance(ALGORITHM)
        val entry = keyStore.getEntry(ALIAS, null) as? KeyStore.PrivateKeyEntry
            ?: throw IllegalStateException("KeyStore entry not found or invalid")
            
        signature.initVerify(entry.certificate.publicKey)
        signature.update(data.toByteArray(Charsets.UTF_8))
        
        return try {
            val signatureBytes = Base64.decode(signatureBase64, Base64.NO_WRAP)
            signature.verify(signatureBytes)
        } catch (e: Exception) {
            false
        }
    }
}
