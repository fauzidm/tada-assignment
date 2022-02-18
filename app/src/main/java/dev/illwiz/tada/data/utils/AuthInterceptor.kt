package dev.illwiz.tada.data.utils

import dev.illwiz.tada.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Add API Key to every Request
 * https://data.rijksmuseum.nl/object-metadata/api/
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}