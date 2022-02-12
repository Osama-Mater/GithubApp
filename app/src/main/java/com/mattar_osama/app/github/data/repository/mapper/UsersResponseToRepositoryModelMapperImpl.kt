package com.mattar_osama.app.github.data.repository.mapper

import com.mattar_osama.app.github.data.repository.model.GithubUserRepositoryModel
import com.mattar_osama.app.github.domain.model.GithubUserDomainModel
import javax.inject.Inject

interface UsersRepositoryToDomainModelMapper {
    fun toDomainModel(githubUsersRepositoryModel: List<GithubUserRepositoryModel>): List<GithubUserDomainModel>
}


class UsersRepositoryToDomainModelMapperImpl @Inject constructor() :
    UsersRepositoryToDomainModelMapper {
    override fun toDomainModel(githubUsersRepositoryModel: List<GithubUserRepositoryModel>): List<GithubUserDomainModel> {
        return githubUsersRepositoryModel.map { userProfile ->
            GithubUserDomainModel(
                userProfile.avatarUrl,
                userProfile.login,
                userProfile.type
            )
        }
    }
}