package com.mattar_osama.app.github.data.repository

import com.mattar_osama.app.github.data.datasource.GithubDataSource
import com.mattar_osama.app.github.data.repository.mapper.ProjectsRepositoryToDomainModelMapper
import com.mattar_osama.app.github.data.repository.mapper.UserDetailsRepositoryToDomainModelMapper
import com.mattar_osama.app.github.data.repository.mapper.UsersRepositoryToDomainModelMapper
import com.mattar_osama.app.github.domain.GithubRepository
import com.mattar_osama.app.github.domain.model.GithubUserDetailsDomainModel
import com.mattar_osama.app.github.domain.model.GithubUserDomainModel
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteDataSource: GithubDataSource,
    private val projectDomainMapper: ProjectsRepositoryToDomainModelMapper,
    private val usersDomainMapper: UsersRepositoryToDomainModelMapper,
    private val userDetailsDomainMapper: UserDetailsRepositoryToDomainModelMapper
) : GithubRepository {

    override suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<ProjectDomainModel>> =
        githubRemoteDataSource.searchRepositories(query, page, sort).map {
            projectDomainMapper.toDomainModel(it)
        }

    override suspend fun searchUsers(query: String, page: Int): Flow<List<GithubUserDomainModel>> =
        githubRemoteDataSource.searchUsers(query, page).map {
            usersDomainMapper.toDomainModel(it)
        }

    override suspend fun getUserDetails(query: String): Flow<GithubUserDetailsDomainModel> =
        supervisorScope {
            githubRemoteDataSource.getUserDetails(query).map {
                userDetailsDomainMapper.toDomainModel(it)
            }
        }
}