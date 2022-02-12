package com.mattar_osama.app.github.data.repository.mapper

import com.mattar_osama.app.github.data.repository.model.GithubUserDetailsRepositoryModel
import com.mattar_osama.app.github.domain.model.GithubUserDetailsDomainModel
import javax.inject.Inject

interface UserDetailsRepositoryToDomainModelMapper {
    fun toDomainModel(githubUserDetailsRepositoryModel: GithubUserDetailsRepositoryModel): GithubUserDetailsDomainModel
}

class UserDetailsRepositoryToDomainModelMapperImpl @Inject constructor() :
    UserDetailsRepositoryToDomainModelMapper {
    override fun toDomainModel(githubUserDetailsRepositoryModel: GithubUserDetailsRepositoryModel): GithubUserDetailsDomainModel {
        return GithubUserDetailsDomainModel(
            githubUserDetailsRepositoryModel.avatarUrl,
            githubUserDetailsRepositoryModel.bio,
            githubUserDetailsRepositoryModel.blog,
            githubUserDetailsRepositoryModel.blog,
            githubUserDetailsRepositoryModel.email,
            githubUserDetailsRepositoryModel.followers,
            githubUserDetailsRepositoryModel.following,
            githubUserDetailsRepositoryModel.location,
            githubUserDetailsRepositoryModel.login,
            githubUserDetailsRepositoryModel.name,
            githubUserDetailsRepositoryModel.twitter_username
        )
    }
}