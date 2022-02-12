package com.mattar_osama.app.github.domain.usecase

import com.mattar_osama.app.github.domain.GithubRepository
import com.mattar_osama.app.github.domain.model.GithubUserDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchUsers {
    suspend fun execute(query: String, page: Int): Flow<List<GithubUserDomainModel>>
}

class SearchUsersImpl @Inject constructor(private val githubRepository: GithubRepository) :
    SearchUsers {
    override suspend fun execute(query: String, page: Int): Flow<List<GithubUserDomainModel>> =
        githubRepository.searchUsers(query, page)
}