package com.arch.biometric

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

interface IBiometryAuthenticator {

    /**
     * Performs user authentication using biometrics-fingerprint/face scan-returns the result of the scan
     *
     * @param requestReason - Text describing the reason for confirmation via biometrics
     * @param failureButtonText - Text of the button to go to the backup verification method in case of unsuccessful biometrics recognition
     *
     * @throws Exception if authentication failed
     *
     * @return true for successful confirmation of biometrics, false for unsuccessful confirmation
     */

    abstract suspend fun checkBiometryAuthentication(
        requestReason: String,
        failureButtonText: String
    ): Boolean

    /**
     * Performs a fingerprint scan availability check
     *
     * @return true if it is possible to use a fingerprint scanner, false - if it is not available
     */

    fun isTouchIdEnabled(): Boolean

    /**
     * Performs the availability check of the FaceID scan
     *
     * @return true if it is possible to use the face scanner, false - if it is not available
     */

    fun isFaceIdEnabled(): Boolean
}

interface IAndroidBiometryAuthenticator : IBiometryAuthenticator {
    fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager)
}