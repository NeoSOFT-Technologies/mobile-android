package com.arch.biometric

import android.content.pm.PackageManager

import android.os.Build

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.Executor
import kotlin.coroutines.suspendCoroutine

internal class AndroidBiometryAuthenticator: IAndroidBiometryAuthenticator {

    var fragmentManager: FragmentManager? = null
    private var _packageManager: PackageManager? = null

    override fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager

        val observer = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                this@AndroidBiometryAuthenticator.fragmentManager = null
                source.lifecycle.removeObserver(this)
            }
        }
        lifecycle.addObserver(observer)
    }

    fun setPackageManager(packageManager: PackageManager) {
        this._packageManager = packageManager
    }

    override suspend fun checkBiometryAuthentication(
        requestReason: String,
        failureButtonText: String): Boolean {

        val fragmentManager =
            fragmentManager
                ?: throw IllegalStateException("can't check biometry without active window")

        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(BIOMETRY_RESOLVER_FRAGMENT_TAG)
        val resolverFragment: ResolverFragment = if (currentFragment != null) {
            currentFragment as ResolverFragment
        } else {
            ResolverFragment().apply {
                fragmentManager
                    .beginTransaction()
                    .add(this, BIOMETRY_RESOLVER_FRAGMENT_TAG)
                    .commitNow()
            }
        }

        return suspendCoroutine<Boolean> { continuation ->
            var resumed = false
            resolverFragment.showBiometricPrompt(
                requestTitle = "Biometry",
                requestReason = requestReason,
                failureButtonText = failureButtonText,
                credentialAllowed = true
            ) {
                if (!resumed) {
                    continuation.resumeWith(it)
                    resumed = true
                }
            }
        }
    }

    /**
     * Performs a fingerprint scan availability check
     *
     * @return true if it is possible to use a fingerprint scanner, false - if it is not available
     */
    override fun isTouchIdEnabled(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }
        return _packageManager?.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
            ?: throw IllegalStateException("can't check touch id enabled without packageManager")
    }

    /**
     * Performs the availability check of the FaceID scan
     *
     * @return true if it is possible to use the face scanner, false - if it is not available
     */
    override fun isFaceIdEnabled(): Boolean {
        return false
    }

    class ResolverFragment : Fragment() {

        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var promptInfo: BiometricPrompt.PromptInfo

        init {
            retainInstance = true
        }

        /**
         * Prepare and show BiometricPrompt system dialog
         *
         * @param requestTitle biometric prompt title
         * @param requestReason biometric prompt reason
         * @param failureButtonText
         * @param credentialAllowed Allows user to authenticate using their lock screen PIN, pattern, or password.
         */
        fun showBiometricPrompt(
            requestTitle: String,
            requestReason: String,
            failureButtonText: String,
            credentialAllowed: Boolean,
            callback: (Result<Boolean>) -> Unit
        ) {
            val context = requireContext()

            executor = ContextCompat.getMainExecutor(context)

            biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                            callback.invoke(Result.failure(Exception(errorCode.toString())))
                        } else {
                            callback.invoke(Result.failure(Exception(errString.toString())))
                        }
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        callback.invoke(Result.success(true))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        callback.invoke(Result.success(false))
                    }
                }
            )

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(requestTitle)
                .setSubtitle(requestReason)
                .apply {
                    if (!credentialAllowed) {
                        this.setNegativeButtonText(failureButtonText)
                    }
                }
                .setDeviceCredentialAllowed(credentialAllowed)
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

    companion object {
        private const val BIOMETRY_RESOLVER_FRAGMENT_TAG = "BiometryControllerResolver"
    }
}