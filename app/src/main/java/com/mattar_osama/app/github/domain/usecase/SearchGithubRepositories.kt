package com.mattar_osama.app.github.domain.usecase

import com.mattar_osama.app.github.domain.GithubRepository
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchGithubRepositories {
    suspend fun execute(query: String, page: Int, sort: String): Flow<List<ProjectDomainModel>>
}

class SearchGithubRepositoriesImpl @Inject constructor(private val githubRepository: GithubRepository) :
    SearchGithubRepositories {
    override suspend fun execute(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<ProjectDomainModel>> = githubRepository.searchRepositories(query, page, sort)
}