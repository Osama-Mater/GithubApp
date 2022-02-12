package com.mattar_osama.app.github.domain

import com.mattar_osama.app.github.domain.model.GithubUserDetailsDomainModel
import com.mattar_osama.app.github.domain.model.GithubUserDomainModel
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<ProjectDomainModel>>

    suspend fun searchUsers(query: String, page: Int): Flow<List<GithubUserDomainModel>>
    suspend fun getUserDetails(query: String): Flow<GithubUserDetailsDomainModel>
}