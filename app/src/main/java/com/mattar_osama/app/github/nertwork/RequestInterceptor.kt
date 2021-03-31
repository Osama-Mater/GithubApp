package com.mattar_osama.app.github.nertwork

import com.mattar_osama.app.github.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(request)
    }
}
