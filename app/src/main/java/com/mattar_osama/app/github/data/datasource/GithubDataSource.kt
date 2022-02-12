package com.mattar_osama.app.github.data.datasource

import com.mattar_osama.app.github.data.repository.model.GithubUserDetailsRepositoryModel
import com.mattar_osama.app.github.data.repository.model.GithubUserRepositoryModel
import com.mattar_osama.app.github.data.repository.model.ProjectRepositoryModel
import kotlinx.coroutines.flow.Flow

interface GithubDataSource {
    suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<ProjectRepositoryModel>>

    suspend fun searchUsers(query: String, page: Int): Flow<List<GithubUserRepositoryModel>>
    suspend fun getUserDetails(query: String): Flow<GithubUserDetailsRepositoryModel>
}