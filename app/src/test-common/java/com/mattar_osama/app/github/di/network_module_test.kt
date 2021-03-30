package com.mattar_osama.app.github.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mattar_osama.app.github.api.GithubReposService
import com.mattar_osama.app.github.nertwork.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun configureNetworkModuleForTest(baseApi: String) = module {

    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.HEADERS
    OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor())
        .addNetworkInterceptor(logger)
        .build()

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create(GithubReposService::class.java) }
}