package com.mattar_osama.app.github.data.datasource.remote

import com.mattar_osama.app.github.data.api.GithubApiService
import com.mattar_osama.app.github.data.datasource.GithubDataSource
import com.mattar_osama.app.github.data.datasource.mapper.ProjectsResponseToRepositoryModelMapper
import com.mattar_osama.app.github.data.datasource.mapper.UserDetailsResponseToRepositoryModelMapper
import com.mattar_osama.app.github.data.datasource.mapper.UsersResponseToRepositoryModelMapper
import com.mattar_osama.app.github.data.datasource.model.GithubUserDetailsRepositoryModel
import com.mattar_osama.app.github.data.datasource.model.GithubUserRepositoryModel
import com.mattar_osama.app.github.data.datasource.model.ProjectRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val projectsResponseToRepositoryModelMapper: ProjectsResponseToRepositoryModelMapper,
    private val usersResponseToRepositoryModelMapper: UsersResponseToRepositoryModelMapper,
    private val userDetailsResponseToRepositoryModelMapper: UserDetailsResponseToRepositoryModelMapper
) : GithubDataSource {
    private val _projectsResponseSharedFlow = MutableStateFlow(listOf<ProjectRepositoryModel>())
    private val projectsResponseSharedFlow = _projectsResponseSharedFlow.asSharedFlow()
    private val _usersResponseSharedFlow = MutableStateFlow(listOf<GithubUserRepositoryModel>())
    private val usersResponseSharedFlow = _usersResponseSharedFlow.asSharedFlow()
    private val _userDetailsResponseSharedFlow = MutableStateFlow(
        GithubUserDetailsRepositoryModel("", "", "", "", "", -1, -1, "", "", "", "")
    )
    private val userDetailsResponseSharedFlow = _userDetailsResponseSharedFlow.asSharedFlow()

    override suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<ProjectRepositoryModel>> {
        try {
            projectsResponseToRepositoryModelMapper.toRepositoryModel(
                githubApiService.searchRepositories(
                    query = query,
                    page = page,
                    sort = sort
                )
            )
                .let {
                    _projectsResponseSharedFlow.emit(it)
                }
        } catch (ex: Exception) {
        }
        return projectsResponseSharedFlow.distinctUntilChanged()
    }

    override suspend fun searchUsers(
        query: String,
        page: Int
    ): Flow<List<GithubUserRepositoryModel>> {
        try {
            usersResponseToRepositoryModelMapper.toRepositoryModel(
                githubApiService.searchUsers(
                    query = query,
                    page = page
                )
            ).let {
                _usersResponseSharedFlow.emit(it)
            }
        } catch (ex: Exception) {
        }
        return usersResponseSharedFlow.distinctUntilChanged()
    }

    override suspend fun getUserDetails(query: String): Flow<GithubUserDetailsRepositoryModel> {
        try {
            userDetailsResponseToRepositoryModelMapper.toRepositoryModel(
                githubApiService.userProfile(
                    query
                )
            ).let {
                _userDetailsResponseSharedFlow.emit(it)
            }
        } catch (ex: Exception) {
        }
        return userDetailsResponseSharedFlow.distinctUntilChanged()
    }
}