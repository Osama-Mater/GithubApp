package com.mattar_osama.app.github.data.di

import com.mattar_osama.app.github.data.api.GithubApiService
import com.mattar_osama.app.github.data.datasource.GithubDataSource
import com.mattar_osama.app.github.data.datasource.mapper.*
import com.mattar_osama.app.github.data.datasource.remote.GithubRemoteDataSourceImpl
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
    fun provideGithubRemoteDataSource(
        githubApiService: GithubApiService,
        projectsResponseToRepositoryModelMapper: ProjectsResponseToRepositoryModelMapper,
        usersResponseToRepositoryModelMapper: UsersResponseToRepositoryModelMapper,
        userDetailsResponseToRepositoryModelMapper: UserDetailsResponseToRepositoryModelMapper
    ): GithubDataSource = GithubRemoteDataSourceImpl(
        githubApiService,
        projectsResponseToRepositoryModelMapper,
        usersResponseToRepositoryModelMapper,
        userDetailsResponseToRepositoryModelMapper
    )

    @Provides
    fun provideProjectsResponseToRepositoryModelMapper(): ProjectsResponseToRepositoryModelMapper =
        ProjectsResponseToRepositoryModelMapperImpl()

    @Provides
    fun provideUsersResponseToRepositoryModelMapper(): UsersResponseToRepositoryModelMapper =
        UsersResponseToRepositoryModelMapperImpl()

    @Provides
    fun provideUserDetailsResponseToRepositoryModelMapper(): UserDetailsResponseToRepositoryModelMapper =
        UserDetailsResponseToRepositoryModelMapperImpl()

    @Provides
    internal fun provideApi(retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)

    @Provides
    internal fun provideRetrofit(
        httpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .client(httpBuilder.build())
        .build()
}