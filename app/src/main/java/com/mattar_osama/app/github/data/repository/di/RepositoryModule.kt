package com.mattar_osama.app.github.data.repository.di

import com.mattar_osama.app.github.data.api.GithubApiService
import com.mattar_osama.app.github.data.datasource.GithubDataSource
import com.mattar_osama.app.github.data.repository.GithubRepositoryImpl
import com.mattar_osama.app.github.data.repository.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {
    @Provides
    fun provideGithubRepository(
        service: GithubApiService, githubRemoteDataSource: GithubDataSource,
        projectDomainMapper: ProjectsRepositoryToDomainModelMapper,
        usersDomainMapper: UsersRepositoryToDomainModelMapper,
        userDetailsDomainMapper: UserDetailsRepositoryToDomainModelMapper
    ): GithubRepositoryImpl {
        return GithubRepositoryImpl(
            service,
            githubRemoteDataSource,
            projectDomainMapper,
            usersDomainMapper,
            userDetailsDomainMapper
        )
    }

    @Provides
    fun provideProjectsRepositoryToDomainModelMapper(): ProjectsRepositoryToDomainModelMapper =
        ProjectsRepositoryToDomainModelMapperImpl()

    @Provides
    fun provideUsersRepositoryToDomainModelMapper(): UsersRepositoryToDomainModelMapper =
        UsersRepositoryToDomainModelMapperImpl()

    @Provides
    fun provideUserDetailsRepositoryToDomainModelMapper(): UserDetailsRepositoryToDomainModelMapper =
        UserDetailsRepositoryToDomainModelMapperImpl()


}