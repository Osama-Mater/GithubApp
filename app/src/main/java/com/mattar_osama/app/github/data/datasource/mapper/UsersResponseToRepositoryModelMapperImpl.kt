package com.mattar_osama.app.github.data.datasource.mapper

import com.mattar_osama.app.github.data.dto.githubusersdto.UserSearchResponseDto
import com.mattar_osama.app.github.data.datasource.model.GithubUserRepositoryModel
import javax.inject.Inject

interface UsersResponseToRepositoryModelMapper {
    fun toRepositoryModel(userSearchResponseDto: UserSearchResponseDto): List<GithubUserRepositoryModel>
}


class UsersResponseToRepositoryModelMapperImpl @Inject constructor() :
    UsersResponseToRepositoryModelMapper {
    override fun toRepositoryModel(userSearchResponseDto: UserSearchResponseDto): List<GithubUserRepositoryModel> {
        return userSearchResponseDto.userProfiles.map { userProfile ->
            GithubUserRepositoryModel(
                avatarUrl = userProfile.avatarUrl,
                login = userProfile.login,
                type = userProfile.type
            )
        }
    }
}