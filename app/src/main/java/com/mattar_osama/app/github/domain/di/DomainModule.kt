package com.mattar_osama.app.github.domain.di

import com.mattar_osama.app.github.domain.GithubRepository
import com.mattar_osama.app.github.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Reusable
    fun provideSearchRepositories(githubRepository: GithubRepository): SearchGithubRepositories =
        SearchGithubRepositoriesImpl(githubRepository)

    @Provides
    @Reusable
    fun provideSearchUsers(githubRepository: GithubRepository): SearchUsers =
        SearchUsersImpl(githubRepository)

    @Provides
    @Reusable
    fun provideGetUserDetails(githubRepository: GithubRepository): GetUserDetails =
        GetUserDetailsImpl(githubRepository)
}