package com.mattar_osama.app.github.data.datasource.mapper

import com.mattar_osama.app.github.data.dto.userprofiledto.GitHubUserProfileDto
import com.mattar_osama.app.github.data.datasource.model.GithubUserDetailsRepositoryModel
import javax.inject.Inject

interface UserDetailsResponseToRepositoryModelMapper {
    fun toRepositoryModel(gitHubUserProfileDto: GitHubUserProfileDto): GithubUserDetailsRepositoryModel
}

class UserDetailsResponseToRepositoryModelMapperImpl @Inject constructor() :
    UserDetailsResponseToRepositoryModelMapper {
    override fun toRepositoryModel(gitHubUserProfileDto: GitHubUserProfileDto): GithubUserDetailsRepositoryModel {
        return GithubUserDetailsRepositoryModel(
            avatarUrl = gitHubUserProfileDto.avatar_url,
            bio = gitHubUserProfileDto.bio,
            blog = gitHubUserProfileDto.blog,
            company = gitHubUserProfileDto.blog,
            email = gitHubUserProfileDto.email,
            followers = gitHubUserProfileDto.followers,
            following = gitHubUserProfileDto.following,
            location = gitHubUserProfileDto.location,
            login = gitHubUserProfileDto.login,
            name = gitHubUserProfileDto.name,
            twitter_username = gitHubUserProfileDto.twitter_username
        )
    }
}