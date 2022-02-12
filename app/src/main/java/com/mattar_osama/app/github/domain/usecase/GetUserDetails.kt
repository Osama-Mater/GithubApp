package com.mattar_osama.app.github.domain.usecase

import com.mattar_osama.app.github.domain.GithubRepository
import com.mattar_osama.app.github.domain.model.GithubUserDetailsDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserDetails {
    suspend fun execute(userName: String): Flow<GithubUserDetailsDomainModel>
}

class GetUserDetailsImpl @Inject constructor(private val githubRepository: GithubRepository) :
    GetUserDetails {
    override suspend fun execute(userName: String): Flow<GithubUserDetailsDomainModel> =
        githubRepository.getUserDetails(userName)
}