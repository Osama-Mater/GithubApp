package com.mattar_osama.app.github.di

import com.mattar_osama.app.github.data.api.GithubReposService
import com.mattar_osama.app.github.data.repository.GithubReposRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideGithubReposRepository(service: GithubReposService): GithubReposRepository {
        return GithubReposRepository(service)
    }

    @Provides
    internal fun provideApi(retrofit: Retrofit): GithubReposService =
        retrofit.create(GithubReposService::class.java)

    @Provides
    internal fun provideRetrofit(
        httpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .client(httpBuilder.build())
        .build()
}