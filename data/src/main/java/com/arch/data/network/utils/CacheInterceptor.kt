package com.arch.data.network.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

/**
 * An interceptor class for applying cache control headers in api calls.
 */
internal class CacheInterceptor(
    var context: Context
) : Interceptor {

    companion object {
        // Cache size and expiry
        const val CACHE_EXPIRY = 60 * 60 * 24 * 7
        const val CACHE_SIZE = (5 * 1024 * 1024).toLong()

        // Cache tags to identify during intercept
        const val CACHE_TAG_FEED = "feed"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (isFeedUrl(request)) {
            request = if (hasNetwork(context)!!) {
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            } else {
                request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$CACHE_EXPIRY"
                    )
                    .build()
            }
        } else {
            request.newBuilder().header("Cache-Control", "no-cache").build()
        }
        return chain.proceed(request)
    }

    private fun isFeedUrl(request: Request): Boolean {
        request.tag(Invocation::class.java)?.let {
            it.method().getAnnotation(Tag::class.java)?.let { tag ->
                return tag.value.equals(CACHE_TAG_FEED)
            }
        }
        return false
    }

    // @Todo change deprecated code
    @SuppressLint("MissingPermission")
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
