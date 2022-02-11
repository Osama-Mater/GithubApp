package com.mattar_osama.app.github.nertwork

import com.mattar_osama.app.github.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
        val request = requestBuilder
            .addHeader("Accept", "application/vnd.github.v3+json")
            .addHeader(
                "Authorization",
                Credentials.basic(BuildConfig.GITHUB_USER_NAME, BuildConfig.GITHUB_TOKEN)
            )
            .build()
        return chain.proceed(request)
    }
}
